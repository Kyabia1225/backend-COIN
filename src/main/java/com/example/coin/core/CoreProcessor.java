package com.example.coin.core;


import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CoreProcessor {
    /**指定问题question及字典的txt模板所在的根目录*/
    private String rootDirPath;

    /**Spark贝叶斯分类器*/
    private NaiveBayesModel nbModel;

    /**分类标签号和问句模板对应表*/
    private Map<Double, String> questionsPattern;

    /**词语和下标的对应表   == 词汇表*/
    private Map<String, Integer> vocabulary;

    /**关键字与其词性的map键值对集合 == 句子抽象*/
    private Map<String, String> abstractMap;

    /** 分类模板索引*/
    int modelIndex = 0;

    public CoreProcessor(String rootDirPath) throws Exception{
        this.rootDirPath = rootDirPath+'/';
        // 加载问题模板
        questionsPattern = loadQuestionTemplates();
        // 加载词汇表
        vocabulary = loadVocabulary();
        System.out.println(vocabulary.size());
        // 加载分类模型，初始化贝叶斯分类器对象
        nbModel = loadClassifierModel();
    }

    /**
     * 问句拆解，套用模板，得到关键word，核心方法实现
     * @param querySentence 问句
     * @return 结果集合（问题模板索引、关键word数组）
     * @throws Exception
     */
    public List<String> analysis(String querySentence) throws Exception {

        /**原始问句*/
        System.out.println("原始句子："+querySentence);
        System.out.println("========HanLP开始分词========");

        /**抽象句子，利用HanPL分词，将关键字进行词性抽象*/
        String abstractStr = queryAbstract(querySentence);
        System.out.println("句子抽象化结果："+abstractStr);

        /**将抽象的句子与Spark训练集中的模板进行匹配，拿到句子对应的模板*/
        String strPattern = queryClassify(abstractStr);
        System.out.println("句子套用模板结果："+strPattern);

        /**模板还原成句子，此时问题已转换为我们熟悉的操作*/
        String finalPattern = sentenceReduction(strPattern);
        System.out.println("原始句子替换成系统可识别的结果："+finalPattern);

        List<String> resultList = new ArrayList<>();
        resultList.add(String.valueOf(modelIndex));
        String[] finalPatternArr = finalPattern.split(" ");
        for (String word : finalPatternArr)
            resultList.add(word);
        return resultList;

    }

    /**
     * 加载问题模板 == 分类器标签
     * @return Map<Double, String> == 序号，问题分类
     */
    public Map<Double,String> loadQuestionTemplates() {
        Map<Double, String> questionsPattern = new HashMap<>(16);
        File file = new File(rootDirPath + "classification.txt");
        BufferedReader br = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            br = new BufferedReader(isr);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(":");
                double index = Double.parseDouble(tokens[0].replace("\uFEFF",""));
                String pattern = tokens[1];
                questionsPattern.put(index, pattern);
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return questionsPattern;
    }

    /**
     * 加载词汇表 == 关键特征 == 与HanLP分词后的单词进行匹配
     * @return
     */
    public Map<String, Integer> loadVocabulary(){
        Map<String, Integer> vocabulary = new HashMap<>();
        File file = new File(rootDirPath + "vocabulary.txt");
        BufferedReader br = null;
        try{
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            br = new BufferedReader(isr);
        }catch (Exception e){
            e.printStackTrace();
        }
        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(":");
                int index = Integer.parseInt(tokens[0].replace("\uFEFF",""));
                String word = tokens[1];
                vocabulary.put(word, index);
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        System.out.println(vocabulary.size());
        return vocabulary;
    }

    public Map<Double, String> loadQuestionSamples(String path) throws IOException{
        File file = new File(rootDirPath + path);
        if(!file.exists()){
            throw new IOException("文件不存在");
        }
        Map<Double, String> seqWithSamples = new HashMap<>(48);
        if(!file.isDirectory()){
            String content = getFileContent(file);
            seqWithSamples.put(0.0,content);
        }

        File[] files = file.listFiles();

        if(files!=null && files.length>0){
            for(int i = 0;i<files.length;i++){
                File sampleFile = files[i];
                String fileName = sampleFile.getName();
                String seqStr = fileName.split(" ")[0];
                String content = getFileContent(sampleFile);
                seqWithSamples.put(Double.parseDouble(seqStr), content);
            }
        }
        return seqWithSamples;
    }

    /**
     * 获取文件内容
     * @param file 文件
     * @return String
     */
    private String getFileContent(File file) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            /**文本的换行符暂定用"`"代替*/
            content.append(line).append("`");
        }
        /**关闭资源*/
        br.close();
        return content.toString();
    }

    /**
     * 句子分词后与词汇表进行key匹配转换为double向量数组
     * @param sentence 句子
     * @return 向量数组
     */
    public double[] sentenceToArrays(String sentence){
        double[] vector = new double[vocabulary.size()];
        /**模板对照词汇表的大小进行初始化，全部为0.0*/
        for (int i = 0; i < vocabulary.size(); i++) {
            vector[i] = 0;
        }

        /** HanLP分词，拿分词的结果和词汇表里面的关键特征进行匹配*/
        Segment segment = HanLP.newSegment();
        List<Term> terms = segment.seg(sentence);
        for (Term term : terms) {
            String word = term.word;
            /**如果命中，0.0 改为 1.0*/
            if (vocabulary.containsKey(word)) {
                int index = vocabulary.get(word);
                vector[index] = 1;
            }
        }
        return vector;
    }

    public String sentenceReduction(String queryPattern){
        Set<String> set = abstractMap.keySet();
        for(String key:set){
            if(queryPattern.contains(key)){
                String value = abstractMap.get(key);
                queryPattern = queryPattern.replace(key, value);
            }
        }
        String extendedQuery = queryPattern;
        abstractMap.clear();
        abstractMap = null;
        return extendedQuery;
    }
    public NaiveBayesModel loadClassifierModel() throws Exception{
        SparkConf conf = new SparkConf().setAppName("CoinNaiveBayesSelector").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        /**
         * 训练集生成
         * labeled point 是一个局部向量，要么是密集型的要么是稀疏型的
         * 用一个label/response进行关联。在MLlib里，labeled points 被用来监督学习算法
         * 我们使用一个double数来存储一个label，因此我们能够使用labeled points进行回归和分类
         */
        List<LabeledPoint> train_list = new LinkedList<>();
        String[] sentences;
        Map<Double, String> seqWithSamples = loadQuestionSamples("templates/");
        if(seqWithSamples == null || seqWithSamples.size() == 0){
            throw new Exception("缺少问题训练样本");
        }

        for (Map.Entry<Double, String> entry : seqWithSamples.entrySet()) {
            Double seq = entry.getKey();
            String sampleContent = entry.getValue();
            sentences = sampleContent.split("`");
            for (String sentence : sentences) {
                double[] array = sentenceToArrays(sentence);
                LabeledPoint train = new LabeledPoint(seq, Vectors.dense(array));
                train_list.add(train);
            }
        }
        /**
         * SPARK的核心是RDD(弹性分布式数据集)
         * Spark是Scala写的,JavaRDD就是Spark为Java写的一套API
         * JavaSparkContext sc = new JavaSparkContext(sparkConf);    //对应JavaRDD
         * SparkContext	    sc = new SparkContext(sparkConf)    ;    //对应RDD
         */
        JavaRDD<LabeledPoint> trainingRDD = sc.parallelize(train_list);
        /**开始训练样本*/
        NaiveBayesModel nb_model = NaiveBayes.train(trainingRDD.rdd());
        /** 记得关闭资源*/
        sc.close();
        /** 返回贝叶斯分类器*/
        return nb_model;
    }
    /**
     * 将HanLp分词后的关键word，用抽象词性xx替换
     * @param querySentence 查询句子
     * @return
     */
    public String queryAbstract(String querySentence) {
        Segment segment = HanLP.newSegment();
        List<Term> terms = segment.seg(querySentence);
        StringBuilder abstractQuery = new StringBuilder();
        abstractMap = new HashMap<>();
        for(Term term:terms){
            if(term.nature.toString().equals("anime")){
                abstractQuery.append("anime ");
                abstractMap.put("anime", term.word);
            }else if(term.nature.toString().equals("character")){
                abstractQuery.append("character ");
                abstractMap.put("character", term.word);
            }else if(term.nature.toString().equals("cv")){
                abstractQuery.append("cv ");
                abstractMap.put("cv", term.word);
            }else if(term.nature.toString().equals("company")){
                abstractQuery.append("company ");
                abstractMap.put("company", term.word);
            }else if(term.nature.toString().equals("director")){
                abstractQuery.append("director ");
                abstractMap.put("director", term.word);
            }else{
                abstractQuery.append(term.word).append(" ");
            }
        }
        System.out.println("========HanLP分词结束========");
        return abstractQuery.toString();
    }

    public String queryClassify(String sentence) throws Exception{
        double[] testArray = sentenceToArrays(sentence);
        Vector v = Vectors.dense(testArray);
        double index = nbModel.predict(v);
        modelIndex = (int)index;
        System.out.println("the model index is " + index);
        Vector vRes = nbModel.predictProbabilities(v);
        double[] probabilities = vRes.toArray();
        System.out.println("============ 问题模板分类概率 =============");
        for (int i = 0; i < probabilities.length; i++) {
            System.out.println("问题模板分类["+i+"]概率："+String.format("%.5f", probabilities[i]));
        }
        System.out.println("============ 问题模板分类概率 =============");
        return questionsPattern.get(index);
    }
}
