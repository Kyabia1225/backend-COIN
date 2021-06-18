package com.example.coin.service.impl;

import com.example.coin.DAO.*;
import com.example.coin.core.CoreProcessor;
import com.example.coin.po.*;
import com.example.coin.service.EntityService;
import com.example.coin.po.*;
import com.example.coin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    private static final String UNFOUND = "没有查找到相关结果。\n";
    @Autowired
    private CoreProcessor coreProcessor;
    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private AnimeCharacterRepository animeCharacterRepository;
    @Autowired
    private AnimeCVRepository animeCVRepository;
    @Autowired
    private AnimeCompanyRepository animeCompanyRepository;
    @Autowired
    private AnimeDirectorRepository animeDirectorRepository;
    @Autowired
    private EntityRepository entityRepository;
    @Autowired RelationRepository relationRepository;

    @Override
    public String answer(String question) throws Exception {
        List<String> strings = coreProcessor.analysis(question);
        int index=Integer.parseInt(strings.get(0))-1;
        String answer="";
        switch(index){
            case 0:
                //anime 评分
                answer=getAnimeScore(strings.get(1));
                break;
            case 1:
                //anime 放送日期
                answer=getAnimeStartDate(strings.get(1));
                break;
            case 2:
                //anime 别名
                answer=getAnimeOtherNames(strings.get(1));
                break;
            case 3:
                //anime 长度
                answer=getLength(strings.get(1));
                break;
            case 4:
                //anime 导演
                answer=getAnimeDirector(strings.get(1));
                break;
            case 5:
                //anime 是什么
                answer=getAnime(strings.get(1));
                break;
            case 6:
                //anime 角色
                answer=getAnimeCharacters(strings.get(1));
                break;
            case 7:
                //anime 声优
                answer=getAnimeCVs(strings.get(1));
                break;
            case 8:
                //cv 别名
                answer=getCVOtherNames(strings.get(1));
                break;
            case 9:
                //cv 性别
                answer=getCVGender(strings.get(1));
                break;
            case 10:
                //cv 是谁
                answer=getCV(strings.get(1));
                break;
            case 11:
                //cv 生日
                answer=getCVBirthday(strings.get(1));
                break;
            case 12:
                //director 别名
                answer=getDirectorOtherNames(strings.get(1));
                break;
            case 13:
                //director 性别
                answer=getDirectorGender(strings.get(1));
                break;
            case 14:
                //director 生日
                answer=getDirectorBirthday(strings.get(1));
                break;
            case 15:
                //director 是谁
                answer=getDirector(strings.get(1));
                break;
            case 16:
                //character 别名
                answer=getCharacterOtherNames(strings.get(1));
                break;
            case 17:
                //character 性别
                answer=getCharacterGender(strings.get(1));
                break;
            case 18:
                //character 是谁
                answer=getCharacter(strings.get(1));
                break;
            case 19:
                //character 生日
                answer=getCharacterBirthday(strings.get(1));
                break;
            case 20:
                //company 别名
                answer=getCompanyOtherNames(strings.get(1));
                break;
            case 21:
                //company 创办日期
                answer=getCompanyBirthday(strings.get(1));
                break;
            case 22:
                //company 是什么
                answer=getCompany(strings.get(1));
                break;
            case 23:
                //cv 配音过的角色
                answer=getCVCharacters(strings.get(1));
                break;
            case 24:
                //cv 配音过的动画
                answer=getCVAnimes(strings.get(1));
                break;
            case 25:
                //cv 配音 character
                answer=getIfCVDubCharacter(strings.get(1),strings.get(3));
                break;
            case 26:
                //cv 配音 anime
                answer=getIfCVDubAnime(strings.get(1),strings.get(3));
                break;
            case 27:
                //character 所在作品
                answer=getCharacterAnimes(strings.get(1));
                break;
            case 28:
                //character 的声优是谁
                answer=getCharacteCVs(strings.get(1));
                break;
            case 29:
                //company 作品
                answer=getCompanyAnimes(strings.get(1));
                break;
            case 30:
                //director 执导作品
                answer=getDirectorAnimes(strings.get(1));
                break;
            case 31:
                //anime 的制作公司
                answer=getAnimeCompany(strings.get(1));
                break;
            case 32:
                //评分大于 m 动画
                answer=getScoreHigherThanXAnime(strings.get(2));
                break;
            case 33:
                //评分小于 m 动画
                answer=getScoreLowerThanXAnime(strings.get(2));
                break;
            case 34:
                //m 年 放送的动画
                answer=getYYYYAnime(strings.get(1));
                break;
            case 35:
                //cv cv 合作作品
                answer=getCVandCVCooperateAnime(strings.get(1),strings.get(2));
                break;
            case 36:
                //m 年 m 月 放送的动画
                answer=getyyyyMMAnime(strings.get(1), strings.get(3));

        }
        return answer;
    }

    private String getAnimeScore(String anime){
        //1:anime 评分
        List<Anime> animeList = animeRepository.findAnimeByTitleLike(anime);
        if(animeList == null) return UNFOUND;
        int size = animeList.size();
        if(size == 0){
            return UNFOUND;
        }else if(size == 1){
            return anime+" 的评分是"+animeList.get(0).getScore() + "\n";
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("查询到以下").append(size).append("个结果: \n");
            for(Anime foundAnime:animeList){
                stringBuilder.append(foundAnime.getTitle()).append(" 的评分是").append(foundAnime.getScore()).append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getAnimeStartDate(String anime){
        List<Anime> animeList = animeRepository.findAnimeByTitleLike(anime);
        if(animeList == null) return UNFOUND;
        int size = animeList.size();
        if(size == 0){
            return UNFOUND;
        }else if(size == 1) {
            return anime + " 的放送日期是" + animeList.get(0).getStartDate() + "\n";
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("查询到以下").append(size).append("个结果: \n");
            for(Anime foundAnime:animeList) {
                stringBuilder.append(foundAnime.getTitle()).append(" 的放送日期是").append(foundAnime.getStartDate()).append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getAnimeOtherNames(String anime){
        List<Anime> animeList = animeRepository.findAnimeByTitleLike(anime);
        if(animeList == null) return UNFOUND;
        int size = animeList.size();
        if(size == 0){
            return UNFOUND;
        }else if(size == 1) {
            return anime + " 的日文名是" + animeList.get(0).getJapaneseName() + "\n";
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("查询到以下").append(size).append("个结果: \n");
            for(Anime foundAnime:animeList) {
                stringBuilder.append(foundAnime.getTitle()).append(" 的日文名是").append(foundAnime.getJapaneseName()).append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getLength(String anime){
        List<Anime> animeList = animeRepository.findAnimeByTitleLike(anime);
        if(animeList == null) return UNFOUND;
        int size = animeList.size();
        if(size == 0){
            return UNFOUND;
        }else if(size == 1) {
            if(animeList.get(0).getLength().equals("1")) {
                return anime + " 共有" + animeList.get(0).getLength()+"集, 推测是剧场版动画\n";
            }else {
                return anime + " 共有" + animeList.get(0).getLength()+"集\n";
            }
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("查询到以下").append(size).append("个结果: \n");
            for(Anime foundAnime:animeList) {
                if(foundAnime.getLength().equals("1"))
                    stringBuilder.append(foundAnime.getTitle()).append(" 共有").append(foundAnime.getLength()).append("集, 推测是剧场版动画\n");
                else{
                    stringBuilder.append(foundAnime.getTitle()).append(" 共有").append(foundAnime.getLength()).append("集\n");
                }
            }
            return stringBuilder.toString();
        }
    }
    private String getAnimeDirector(String anime){
        //5:anime 导演
        List<Anime> animeList = animeRepository.findAnimeByTitleLike(anime);
        if(animeList == null) return UNFOUND;
        int size = animeList.size();
        if(size == 0){
            return UNFOUND;
        }else if(size == 1) {
            if(animeList.get(0).getDirector()!=null) {
                return anime + " 的导演是" + animeList.get(0).getDirector() + "\n";
            }else{
                return anime + " 的导演未知\n";
            }
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("查询到以下").append(size).append("个结果: \n");
            for(Anime foundAnime:animeList) {
                if(foundAnime.getDirector()!=null) {
                    stringBuilder.append(foundAnime.getTitle()).append(" 的导演是").append(foundAnime.getDirector()).append("\n");
                }else{
                    stringBuilder.append(foundAnime.getTitle()).append(" 的导演未知\n");
                }
            }
            return stringBuilder.toString();
        }
    }
    private String getAnime(String anime){
        List<Anime> animeList = animeRepository.findAnimeByTitleLike(anime);
        if(animeList == null) return UNFOUND;
        int size = animeList.size();
        if(size == 0){
            return UNFOUND;
        }else if(size == 1) {
            return animeList.get(0).getDescription()+"\n";
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("查询到以下").append(size).append("个结果: \n");
            for(Anime foundAnime:animeList) {
                stringBuilder.append(foundAnime.getTitle()).append(" 简介如下:\n").append(foundAnime.getDescription()).append("\n\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getAnimeCharacters(String anime){
        List<Anime> animeList = animeRepository.findAnimeByTitleLike(anime);
        if(animeList == null) return UNFOUND;
        int size = animeList.size();
        if(size == 0){
            return UNFOUND;
        }else if(size == 1){
            List<String> characterList = animeList.get(0).getCharacterList();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(animeList.get(0).getTitle()).append("有以下主要角色: \n");
            for(String characterId:characterList){
                AnimeCharacter character = animeCharacterRepository.findAnimeCharacterByCharacterId(characterId);
                stringBuilder.append(character.getName()).append(":").append(character.getDescription()).append("\n");
            }
            return stringBuilder.toString();
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("查询到以下").append(size).append("个结果: \n\n");
            for(Anime foundAnime:animeList){
                List<String> characterList = foundAnime.getCharacterList();
                stringBuilder.append(foundAnime.getTitle()).append("有以下主要角色: \n");
                for(String characterId:characterList){
                    AnimeCharacter character = animeCharacterRepository.findAnimeCharacterByCharacterId(characterId);
                    stringBuilder.append(character.getName()).append(":").append(character.getDescription()).append("\n");
                }
            }
            return stringBuilder.toString();
        }
    }
    private String getAnimeCVs(String anime){
        List<Anime> animeList = animeRepository.findAnimeByTitleLike(anime);
        if(animeList == null) return UNFOUND;
        int size = animeList.size();
        if(size == 0){
            return UNFOUND;
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            if(size>1){
                stringBuilder.append("查询到以下").append(size).append("个结果: \n\n");
            }
            for(Anime foundAnime:animeList) {
                List<String> characterList = foundAnime.getCharacterList();
                stringBuilder.append(foundAnime.getTitle()).append("有以下主要配音演员: \n");
                for (String characterId : characterList) {
                    Entity animeCharacter = entityRepository.findEntityByBgmIdAndType(characterId, "AnimeCharacter");
                    for (String cv : animeCharacter.getRelatesTo().values()) {
                        Entity cvEntity = entityRepository.findEntityById(cv);
                        if (cvEntity.getType().equals("AnimeCV")) {
                            stringBuilder.append(cvEntity.getName()).append("配音了").append(animeCharacter.getName()).append("\n");
                        }
                    }
                }
            }
            return stringBuilder.toString();
        }
    }
    private String getCVOtherNames(String cv){
        List<AnimeCV> cvList = animeCVRepository.findAnimeCVByNameLike(cv);
        if(cvList == null) return UNFOUND;
        if(cvList.size() == 0){
            return UNFOUND;
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            if(cvList.size()>1){
                stringBuilder.append("查询到以下").append(cvList.size()).append("个声优结果: \n\n");
            }
            for(AnimeCV CV:cvList){
                stringBuilder.append(CV.getName()).append("有这些别名: ");
                for(String otherName:CV.getOtherNames()){
                    stringBuilder.append(otherName).append(" ");
                }
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getCVGender(String cv){
        List<AnimeCV> cvList = animeCVRepository.findAnimeCVByNameLike(cv);
        if(cvList == null) return UNFOUND;
        if(cvList.size() == 0){
            return UNFOUND;
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            if(cvList.size()>1){
                stringBuilder.append("查询到以下").append(cvList.size()).append("个声优结果: \n\n");
            }
            for(AnimeCV CV:cvList){
                stringBuilder.append(CV.getName()).append("性别为").append(CV.getGender()).append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getCV(String cv){
        List<AnimeCV> cvList = animeCVRepository.findAnimeCVByNameLike(cv);
        if(cvList == null) return UNFOUND;
        if(cvList.size() == 0){
            return UNFOUND;
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            if(cvList.size()>1){
                stringBuilder.append("查询到以下").append(cvList.size()).append("个声优结果: \n\n");
            }
            for(AnimeCV CV:cvList){
                stringBuilder.append(CV.getName()).append("信息如下:\n").append(CV.getDescription()).append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getCVBirthday(String cv){
        List<AnimeCV> cvList = animeCVRepository.findAnimeCVByNameLike(cv);
        if(cvList == null) return UNFOUND;
        if(cvList.size() == 0){
            return UNFOUND;
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            if(cvList.size()>1){
                stringBuilder.append("查询到以下").append(cvList.size()).append("个声优结果: \n\n");
            }
            for(AnimeCV CV:cvList){
                stringBuilder.append(CV.getName()).append("生日是").append(CV.getBirthday()).append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getDirectorOtherNames(String director){
        List<AnimeDirector> directorList = animeDirectorRepository.findAnimeDirectorByNameLike(director);
        if(directorList == null) return UNFOUND;
        if(directorList.size() == 0){
            return UNFOUND;
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            if(directorList.size()>1){
                stringBuilder.append("查询到以下").append(directorList.size()).append("个导演结果: \n\n");
            }
            for(AnimeDirector direc:directorList){
                stringBuilder.append(direc.getName()).append("有这些别名: ");
                for(String otherName:direc.getOtherNames()){
                    stringBuilder.append(otherName).append(" ");
                }
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getDirectorGender(String director){
        List<AnimeDirector> directorList = animeDirectorRepository.findAnimeDirectorByNameLike(director);
        if(directorList == null) return UNFOUND;
        if(directorList.size() == 0){
            return UNFOUND;
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            if(directorList.size()>1){
                stringBuilder.append("查询到以下").append(directorList.size()).append("个导演结果: \n\n");
            }
            for(AnimeDirector direc:directorList){
                stringBuilder.append(direc.getName()).append("性别为").append(direc.getGender()).append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getDirectorBirthday(String director){
        List<AnimeDirector> directorList = animeDirectorRepository.findAnimeDirectorByNameLike(director);
        if(directorList == null) return UNFOUND;
        if(directorList.size() == 0){
            return UNFOUND;
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            if(directorList.size()>1){
                stringBuilder.append("查询到以下").append(directorList.size()).append("个导演结果: \n\n");
            }
            for(AnimeDirector direc:directorList){
                stringBuilder.append(direc.getName()).append("生日是").append(direc.getBirthday()).append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getDirector(String director){
        List<AnimeDirector> directorList = animeDirectorRepository.findAnimeDirectorByNameLike(director);
        if(directorList == null) return UNFOUND;
        if(directorList.size() == 0){
            return UNFOUND;
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            if(directorList.size()>1){
                stringBuilder.append("查询到以下").append(directorList.size()).append("个导演结果: \n\n");
            }
            for(AnimeDirector direc:directorList){
                stringBuilder.append(direc.getName()).append("信息如下:\n").append(direc.getDescription()).append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getCharacterOtherNames(String character){
        List<AnimeCharacter> characterList = animeCharacterRepository.findAnimeCharacterByNameContaining(character);
        if(characterList == null) return UNFOUND;
        if(characterList.size() == 0){
            return UNFOUND;
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            if(characterList.size()>1){
                stringBuilder.append("查询到以下").append(characterList.size()).append("个动画角色结果: \n\n");
            }
            for(AnimeCharacter chara:characterList){
                stringBuilder.append(chara.getName()).append("有这些别名: ");
                for(String otherName:chara.getOtherNames()){
                    stringBuilder.append(otherName).append(" ");
                }
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getCharacterGender(String character){
        return "";
    }

    private String getCharacter(String character){
        List<AnimeCharacter> c=animeCharacterRepository.findAnimeCharacterByName(character);
        List<AnimeCharacter> charas=animeCharacterRepository.findAnimeCharactersByOtherNamesContaining(character);
        charas.addAll(c);
        charas = charas.stream().distinct().collect(Collectors.toList());
        if(charas.size() == 0){
            return UNFOUND;
        }
        StringBuilder res= new StringBuilder();
        res.append("查询到以下").append(String.valueOf(charas.size())).append("个结果\n");
        for(AnimeCharacter chara:charas){
            res.append("角色名: ").append(chara.getName()).append("\n");
            res.append("角色介绍:").append(chara.getDescription()).append("\n");
            //TODO 加入出自作品
        }
        return res.toString();
    }
    private String getCharacterBirthday(String character){
        List<AnimeCharacter> c=animeCharacterRepository.findAnimeCharacterByName(character);
        List<AnimeCharacter> charas=animeCharacterRepository.findAnimeCharactersByOtherNamesContaining(character);
        charas.addAll(c);
        charas = charas.stream().distinct().collect(Collectors.toList());
        if(charas.size() == 0){
            return UNFOUND;
        }
        StringBuilder res= new StringBuilder();
        res.append("查询到以下").append(String.valueOf(charas.size())).append("个结果\n");
        for(AnimeCharacter chara:charas){
            res.append("角色 ").append(chara.getName()).append("的生日是");
            res.append(chara.getBirthday()).append("\n");
        }
        return res.toString();
    }
    private String getCompanyOtherNames(String company){
        List<AnimeCompany> companies = animeCompanyRepository.findAnimeCompanyByNameLike(company);
        List<AnimeCompany> companies1=animeCompanyRepository.findAnimeCompanyByOtherNamesContaining(company);
        StringBuilder res= new StringBuilder();
        companies.addAll(companies1);
        companies = companies.stream().distinct().collect(Collectors.toList());
        if(companies.size() == 0){
            return UNFOUND;
        }
        res.append("查询到以下").append(String.valueOf(companies.size())).append("个结果\n");
        for(AnimeCompany comp:companies){
            res.append("公司 ").append(comp.getName()).append("的别名有");
            List<String> otherNames=comp.getOtherNames();
            for(String othername:otherNames){
                res.append(othername).append(" ");
            }
            res.append("\n");
        }
        return res.toString();
    }
    private String getCompanyBirthday(String company){
        List<AnimeCompany> companies = animeCompanyRepository.findAnimeCompanyByNameLike(company);
        List<AnimeCompany> companies1=animeCompanyRepository.findAnimeCompanyByOtherNamesContaining(company);
        StringBuilder res= new StringBuilder();
        companies.addAll(companies1);
        companies = companies.stream().distinct().collect(Collectors.toList());
        if(companies.size() == 0){
            return UNFOUND;
        }
        res.append("查询到以下").append(String.valueOf(companies.size())).append("个结果\n");
        for(AnimeCompany comp:companies){
            res.append("公司 ").append(comp.getName()).append("的创办日期是");
            res.append(comp.getBirthday());
            res.append("\n");
        }
        return res.toString();
    }
    private String getCompany(String company){
        List<AnimeCompany> companies = animeCompanyRepository.findAnimeCompanyByNameLike(company);
        List<AnimeCompany> companies1=animeCompanyRepository.findAnimeCompanyByOtherNamesContaining(company);
        StringBuilder res= new StringBuilder();
        companies.addAll(companies1);
        if(companies.size() == 0){
            return UNFOUND;
        }
        res.append("查询到以下").append(String.valueOf(companies.size())).append("个结果\n");
        for(AnimeCompany comp:companies){
            res.append("公司 ").append(comp.getName()).append("的创办日期是");
            res.append(comp.getBirthday());
            res.append("\n");
            res.append("公司的别名有");
            List<String> otherNames=comp.getOtherNames();
            for(String othername:otherNames){
                res.append(othername).append(" ");
            }
            res.append("\n");
            res.append("公司介绍:").append(comp.getDescription()).append("\n");
        }
        return res.toString();
    }
    private String getCVCharacters(String cvName){
        StringBuilder res= new StringBuilder();
        List<AnimeCV> cvs=animeCVRepository.findAnimeCVByNameLike(cvName);
        List<AnimeCV> cvs2=animeCVRepository.findAnimeCVByOtherNamesContaining(cvName);
        cvs.addAll(cvs2);
        cvs = cvs.stream().distinct().collect(Collectors.toList());
        if(cvs.size() == 0){
            return UNFOUND;
        }
        res.append("查询到以下").append(String.valueOf(cvs.size())).append("个结果\n");
        for(AnimeCV cv:cvs){
            Entity e=entityRepository.findEntityByBgmIdAndType(cv.getCvId(),"AnimeCV");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();

            res.append("声优").append(cv.getName()).append("扮演的角色有以下:").append("\n");
            Set<String> charaList=new HashSet<String>();
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("扮演")){
                    charaList.add(entityRepository.findEntityById(entry.getValue().toString()).getName());
                    //res+=entityRepository.findEntityById(entry.getValue().toString()).getName()+" ";
                }
            }
            for(String s:charaList){
                res.append(s).append(" ");
            }
        }
        return res.toString();
    }
    private String getCVAnimes(String cvName){
        StringBuilder res= new StringBuilder();
        List<AnimeCV> cvs=animeCVRepository.findAnimeCVByNameLike(cvName);
        List<AnimeCV> cvs2=animeCVRepository.findAnimeCVByOtherNamesContaining(cvName);
        cvs.addAll(cvs2);
        cvs = cvs.stream().distinct().collect(Collectors.toList());
        if(cvs.size() == 0){
            return UNFOUND;
        }
        res.append("查询到以下").append(String.valueOf(cvs.size())).append("个结果\n");
        for(AnimeCV cv:cvs){
            Entity e=entityRepository.findEntityByBgmIdAndType(cv.getCvId(),"AnimeCV");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();

            res.append("声优").append(cv.getName()).append("配音的动画有以下:").append("\n");
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("配音")){
                    res.append(entityRepository.findEntityById(entry.getValue().toString()).getName()).append("    ");
                }
            }
        }
        return res.toString();
    }
    private String getIfCVDubCharacter(String cv,String character){

        return "";
    }
    private String getIfCVDubAnime(String cv,String anime){
        return "";
    }
    private String getCharacterAnimes(String character){
        StringBuilder res= new StringBuilder();
        List<AnimeCharacter> charas=animeCharacterRepository.findAnimeCharacterByNameLike(character);
        List<AnimeCharacter> charas2=animeCharacterRepository.findAnimeCharactersByOtherNamesContaining(character);
        charas.addAll(charas2);
        charas = charas.stream().distinct().collect(Collectors.toList());
        if(charas.size() == 0){
            return UNFOUND;
        }
        res.append("查询到以下").append(String.valueOf(charas.size())).append("个结果\n");
        for(AnimeCharacter chara:charas){
            Entity e=entityRepository.findEntityByBgmIdAndType(chara.getCharacterId(),"AnimeCharacter");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();
            res.append("角色").append(chara.getName()).append("出演的动画有以下:").append("\n");
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("出演")){
                    res.append(entityRepository.findEntityById(entry.getValue().toString()).getName()).append("    ");
                }
            }
        }
        return res.toString();
    }
    private String getCharacteCVs(String character){
        StringBuilder res= new StringBuilder();
        List<AnimeCharacter> charas=animeCharacterRepository.findAnimeCharacterByNameLike(character);
        List<AnimeCharacter> charas2=animeCharacterRepository.findAnimeCharactersByOtherNamesContaining(character);
        charas.addAll(charas2);
        charas = charas.stream().distinct().collect(Collectors.toList());
        if(charas.size() == 0){
            return UNFOUND;
        }
        res.append("查询到以下").append(String.valueOf(charas.size())).append("个结果\n");
        for(AnimeCharacter chara:charas){
            Entity e=entityRepository.findEntityByBgmIdAndType(chara.getCharacterId(),"AnimeCharacter");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();

            res.append("角色").append(chara.getName()).append("的声优有以下:").append("\n");
            Set<String> cvList=new HashSet<String>();
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("扮演")){
                    cvList.add(entityRepository.findEntityById(entry.getValue().toString()).getName()+" ");
                    //res+=entityRepository.findEntityById(entry.getValue().toString()).getName()+" ";
                }
            }
            for(String s:cvList){
                res.append(s).append(" ");
            }
        }

        return res.toString();
    }
    private String getCompanyAnimes(String company){
        StringBuilder res= new StringBuilder();
        List<AnimeCompany> comps=animeCompanyRepository.findAnimeCompanyByNameLike(company);
        List<AnimeCompany> comps2=animeCompanyRepository.findAnimeCompanyByOtherNamesContaining(company);
        comps.addAll(comps2);
        comps = comps.stream().distinct().collect(Collectors.toList());
        if(comps.size() == 0){
            return UNFOUND;
        }
        res.append("查询到以下").append(String.valueOf(comps.size())).append("个结果\n");
        for(AnimeCompany comp:comps){
            Entity e=entityRepository.findEntityByBgmIdAndType(comp.getCompanyId(),"AnimeCompany");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();

            res.append("公司").append(comp.getName()).append("制作的动画有以下:").append("\n");
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("制作")){
                    res.append(entityRepository.findEntityById(entry.getValue().toString()).getName()).append("    ");
                }
            }
        }
        return res.toString();
    }
    private String getDirectorAnimes(String director){
        StringBuilder res= new StringBuilder();
        List<AnimeDirector> dirs=animeDirectorRepository.findAnimeDirectorByNameLike(director);
        List<AnimeDirector> dirs2=animeDirectorRepository.findAnimeDirectorByOtherNamesContaining(director);
        dirs.addAll(dirs2);
        dirs = dirs.stream().distinct().collect(Collectors.toList());
        if(dirs.size() == 0){
            return UNFOUND;
        }
        res.append("查询到以下").append(String.valueOf(dirs.size())).append("个结果\n");
        for(AnimeDirector dir:dirs){
            Entity e=entityRepository.findEntityByBgmIdAndType(dir.getDirectorId(),"AnimeDirector");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();
            res.append("导演").append(dir.getName()).append("执导的动画有以下:").append("\n");
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("导演")){
                    res.append(entityRepository.findEntityById(entry.getValue().toString()).getName()).append("    ");
                }
            }
            res.append("\n");
        }
        return res.toString();
    }
    private String getAnimeCompany(String anime){
        StringBuilder res= new StringBuilder();
        List<Anime> animes=animeRepository.findAnimeByTitleLike(anime);
        List<Anime> animes2=animeRepository.findAnimeByJapaneseNameLike(anime);
        animes.addAll(animes2);
        animes = animes.stream().distinct().collect(Collectors.toList());
        if(animes.size() == 0){
            return UNFOUND;
        }
        res.append("查询到以下").append(String.valueOf(animes.size())).append("个结果\n");
        for(Anime ani:animes){
            Entity e=entityRepository.findEntityByBgmIdAndType(ani.getAnimeId(),"Anime");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();
            res.append("动画").append(ani.getTitle()).append("的制作公司是:").append("\n");
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("制作")){
                    res.append(entityRepository.findEntityById(entry.getValue().toString()).getName()).append("    ");
                }
            }
            res.append("\n");
        }
        return res.toString();
    }
    private String getAnimeDirectors(String anime){
        StringBuilder res= new StringBuilder();
        List<Anime> animes=animeRepository.findAnimeByTitleLike(anime);
        List<Anime> animes2=animeRepository.findAnimeByJapaneseNameLike(anime);
        animes.addAll(animes2);
        animes = animes.stream().distinct().collect(Collectors.toList());
        if(animes.size() == 0){
            return UNFOUND;
        }
        res.append("查询到以下").append(String.valueOf(animes.size())).append("个结果\n");
        for(Anime ani:animes){
            Entity e=entityRepository.findEntityByBgmIdAndType(ani.getAnimeId(),"Anime");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();
            res.append("动画").append(ani.getTitle()).append("的导演是:").append("\n");
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("导演")){
                    res.append(entityRepository.findEntityById(entry.getValue().toString()).getName()).append(" ");
                }
            }
            res.append("\n");
        }
        return res.toString();
    }
    private String getScoreHigherThanXAnime(String score){
        System.out.println(score);
        StringBuilder res= new StringBuilder();
        Double sc=Double.parseDouble(score);
        List<Anime> animes=animeRepository.findAnimeByScoreGreaterThan(sc);
        if(animes.size() == 0){
            return UNFOUND;
        }
        for(Anime anime:animes){
            res.append(anime.getTitle()).append(" ");
        }
        return res.toString();
    }
    private String getScoreLowerThanXAnime(String score){
        System.out.println(score);
        StringBuilder res= new StringBuilder();
        Double sc=Double.parseDouble(score);
        List<Anime> animes=animeRepository.findAnimeByScoreLessThan(sc);
        if(animes.size() == 0){
            return UNFOUND;
        }
        for(Anime anime:animes){
            res.append(anime.getTitle()).append(" ");
        }
        return res.toString();
    }
    private String getYYYYAnime(String year){
        List<Anime> animeList = animeRepository.findAnimeByStartDateStartsWith(year);
        if(animeList == null){
            return UNFOUND;
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("查询到以下动画播放于").append(year).append("年:\n");
            for(Anime anime:animeList){
                stringBuilder.append(anime.getTitle()).append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getCVandCVCooperateAnime(String cv1, String cv2){
        List<AnimeCV> cv1List = animeCVRepository.findAnimeCVByNameLike(cv1);
        List<AnimeCV> cv2List = animeCVRepository.findAnimeCVByNameLike(cv2);
        StringBuilder stringBuilder = new StringBuilder();
        if(cv1List == null||cv1List.size() == 0){
            stringBuilder.append("查找不到声优").append(cv1);
            return stringBuilder.toString();
        }else if(cv2List == null || cv2List.size() == 0){
            stringBuilder.append("查找不到声优").append(cv2);
            return stringBuilder.toString();
        }else{
            AnimeCV CV1 = cv1List.get(0);
            AnimeCV CV2 = cv2List.get(0);
            Entity cv1Entity = entityRepository.findEntityByBgmIdAndType(CV1.getCvId(), "AnimeCV");
            Entity cv2Entity = entityRepository.findEntityByBgmIdAndType(CV2.getCvId(), "AnimeCV");
            Set<String> set1 = new HashSet<>();
            for(String id:cv1Entity.getRelatesTo().values()){
                Entity e = entityRepository.findEntityById(id);
                if(e.getType().equals("Anime")){
                    set1.add(e.getId());
                }
            }
            Set<String> set2 = new HashSet<>(cv2Entity.getRelatesTo().values());
            set1.retainAll(set2);
            if(set1.size() == 0){
                stringBuilder.append(CV1.getName()).append("没有与").append(CV2.getName()).append("合作的动画\n");
                return stringBuilder.toString();
            }
            stringBuilder.append(CV1.getName()).append("与").append(CV2.getName()).append("共同合作的动画有: \n");
            for(String id:set1){
               stringBuilder.append(entityRepository.findEntityById(id).getName()).append(" ");
            }
            return stringBuilder.toString();
        }
    }

    private String getyyyyMMAnime(String year, String month){
        month = month.replace("一","1").replace("二","2").replace("三","3")
        .replace("四","4").replace("五","5").replace("六","6")
        .replace("七","7").replace("八","8").replace("九","9")
        .replace("十","10").replace("十一","11").replace("十二","12");
        List<Anime> animeList = animeRepository.findAnimeByStartDateStartsWith(year+"-"+month);
        if(animeList == null||animeList.size() == 0){
            return UNFOUND;
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("查询到以下动画播放于").append(year).append("年").append(month).append("月\n");
            for(Anime anime:animeList){
                stringBuilder.append(anime.getTitle()).append("\n");
            }
            return stringBuilder.toString();
        }
    }


}

