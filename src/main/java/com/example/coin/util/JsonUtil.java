package com.example.coin.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.example.coin.po.Anime;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtil {
    public static Reader readJsonFile(String fileName)throws FileNotFoundException {
        return new FileReader(fileName);
    }

    public static void analyseAnimeJson(Reader reader){
        JSONReader jsonArray = new JSONReader(reader);
        jsonArray.startArray();
        while (jsonArray.hasNext()){
            JSONObject jsonOne = (JSONObject)jsonArray.readObject();
            Anime anime = new Anime();
            anime.setAnimeId((String) jsonOne.get("anime_id"));
            anime.setJapaneseName((String) jsonOne.get("JapaneseName"));
            anime.setTitle((String) jsonOne.get("title"));
            anime.setLength(Integer.parseInt((String) jsonOne.get("length")));
            //日期需要统一格式，如果多个日期选择最早的
            {
                String date = (String)  jsonOne.get("startDate");
                Pattern pattern = Pattern.compile("\\d{4}年\\d{1,2}月\\d{1,2}日|\\d{4}-\\d{1,2}-\\d{1,2}");
                Matcher matcher = pattern.matcher(date);
                String addressedDate = null;
                if(matcher.find()){
                    addressedDate = matcher.group().replace("年", "-").replace("月", "-").replace("日", "");
                }else{
                    addressedDate = null;
                }
                anime.setStartDate(addressedDate);
            }
            //导演
            anime.setDirector((String) jsonOne.get("director"));
            //导演id
            anime.setDirectorId((String) jsonOne.get("director_id"));
            //评分
            anime.setScore(Double.parseDouble((String) jsonOne.get("score")));
            //排名
            anime.setRanking(Integer.parseInt((String) jsonOne.get("ranking")));
            //动画描述
            anime.setDescription((String) jsonOne.get("description"));
            //处理角色列表
            {
                JSONArray characters = (JSONArray) jsonOne.get("characters");
                List<String> characterList = new ArrayList<>(characters.size());
                for (Object tmp : characters) {
                    characterList.add((String) ((JSONObject) tmp).get("character_id"));
                }
                anime.setCharacterList(characterList);
            }
        }
        jsonArray.endArray();
        jsonArray.close();
    }
}
