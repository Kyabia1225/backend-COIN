package com.example.coin.service.impl;

import com.example.coin.DAO.*;
import com.example.coin.core.CoreProcessor;
import com.example.coin.po.*;
import com.example.coin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
                answer=getCompanyOhterNames(strings.get(1));
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
                //anime 的导演
                answer=getAnimeDirectors(strings.get(1));
                break;
            case 33:
                //评分大于 m 动画
                answer=getScoreHigherThanXAnime(strings.get(2));
                break;
            case 34:
                //评分小于 m 动画
                answer=getScoreLowerThanXAnime(strings.get(2));
                break;
            case 35:
                //m 年 放送的动画
                answer=getYYYYAnime(strings.get(1));
                break;
            case 36:
                //cv cv 合作作品
                answer=getCVandCVCooperateAnime(strings.get(1),strings.get(3));
                break;

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
            }
            return stringBuilder.toString();
        }
    }
    private String getCharacterGender(String character){
        return "";
    }
    private String getCharacter(String character){
        return "";
    }
    private String getCharacterBirthday(String character){
        return "";
    }
    private String getCompanyOhterNames(String company){
        return "";
    }
    private String getCompanyBirthday(String company){
        return "";
    }
    private String getCompany(String company){
        return "";
    }
    private String getCVCharacters(String cv){
        return "";
    }
    private String getCVAnimes(String cv){
        return "";
    }
    private String getIfCVDubCharacter(String cv,String character){
        return "";
    }
    private String getIfCVDubAnime(String cv,String anime){
        return "";
    }
    private String getCharacterAnimes(String character){
        return "";
    }
    private String getCharacteCVs(String character){
        return "";
    }
    private String getCompanyAnimes(String company){
        return "";
    }
    private String getDirectorAnimes(String director){
        return "";
    }
    private String getAnimeCompany(String anime){
        return "";
    }
    private String getAnimeDirectors(String anime){
        return "";
    }
    private String getScoreHigherThanXAnime(String score){
        return "";
    }
    private String getScoreLowerThanXAnime(String score){
        return "";
    }
    private String getYYYYAnime(String year){
        return "";
    }
    private String getCVandCVCooperateAnime(String cv1, String cv2){
        return "";
    }

}

