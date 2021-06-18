package com.example.coin.service.impl;

import com.example.coin.DAO.*;
import com.example.coin.core.CoreProcessor;
import com.example.coin.po.Anime;
import com.example.coin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
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
        List<Anime> animes = animeRepository.findAnimeByTitleLike(anime);
        int size = animes.size();
        if(size == 0){
            return "没有查找到相关结果。";
        }else if(size == 1){
            return anime+" 的评分是"+animes.get(0).getScore();
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("查询到以下").append(size).append("个结果: \n");
            for(Anime foundAnime:animes){
                stringBuilder.append(foundAnime.getTitle()).append(" 的评分是").append(foundAnime.getScore()).append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getAnimeStartDate(String anime){
        List<Anime> animes = animeRepository.findAnimeByTitleLike(anime);int size = animes.size();
        if(size == 0){
            return "没有查找到相关结果。";
        }else if(size == 1) {
            return anime + " 的放送日期是" + animes.get(0).getStartDate();
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("查询到以下").append(size).append("个结果: \n");
            for(Anime foundAnime:animes) {
                stringBuilder.append(foundAnime.getTitle()).append(" 的放送日期是").append(foundAnime.getStartDate()).append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getAnimeOtherNames(String anime){
        List<Anime> animes = animeRepository.findAnimeByTitleLike(anime);int size = animes.size();
        if(size == 0){
            return "没有查找到相关结果。";
        }else if(size == 1) {
            return anime + " 的日文名是" + animes.get(0).getJapaneseName();
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("查询到以下").append(size).append("个结果: \n");
            for(Anime foundAnime:animes) {
                stringBuilder.append(foundAnime.getTitle()).append(" 的日文名是").append(foundAnime.getJapaneseName()).append("\n");
            }
            return stringBuilder.toString();
        }
    }
    private String getLength(String anime){
        return "";
    }
    private String getAnimeDirector(String anime){
        //5:anime 导演
        return "";
    }
    private String getAnime(String anime){
        //TODO 整体的介绍一下这个动画，各方面信息都可以有
        return "";
    }
    private String getAnimeCharacters(String anime){
        return "";
    }
    private String getAnimeCVs(String anime){
        return "";
    }
    private String getCVOtherNames(String cv){
        return "";
    }
    private String getCVGender(String cv){
        return "";
    }
    private String getCV(String cv){
        return "";
    }
    private String getCVBirthday(String cv){
        return "";
    }
    private String getDirectorOtherNames(String director){
        return "";
    }
    private String getDirectorGender(String director){
        return "";
    }
    private String getDirectorBirthday(String director){
        return "";
    }
    private String getDirector(String director){
        return "";
    }
    private String getCharacterOtherNames(String character){
        return "";
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

