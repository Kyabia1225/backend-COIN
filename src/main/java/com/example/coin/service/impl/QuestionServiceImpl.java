package com.example.coin.service.impl;

import com.example.coin.DAO.*;
import com.example.coin.core.CoreProcessor;
import com.example.coin.po.*;
import com.example.coin.service.EntityService;
import com.example.coin.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private RelationRepository relationRepository;

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
        List<AnimeCharacter> c=animeCharacterRepository.findAnimeCharacterByName(character);
        List<AnimeCharacter> charas=animeCharacterRepository.findAnimeCharactersByOtherNamesContaining(character);
        charas.addAll(c);
        String res="";
        res+="查询到以下"+String.valueOf(charas.size())+"个结果\n";
        for(AnimeCharacter chara:charas){
            res+="角色名: "+chara.getName()+"\n";
            res+="角色介绍:"+chara.getDescription()+"\n";
            //TODO 加入出自作品
        }
        return res;
    }
    private String getCharacterBirthday(String character){
        List<AnimeCharacter> c=animeCharacterRepository.findAnimeCharacterByName(character);
        List<AnimeCharacter> charas=animeCharacterRepository.findAnimeCharactersByOtherNamesContaining(character);
        charas.addAll(c);
        String res="";
        res+="查询到以下"+String.valueOf(charas.size())+"个结果\n";
        for(AnimeCharacter chara:charas){
            res+="角色 "+chara.getName()+"的生日是";
            res+=chara.getBirthday()+"\n";
        }
        return res;
    }
    private String getCompanyOhterNames(String company){
        List<AnimeCompany> companies = animeCompanyRepository.findAnimeCompanyByNameLike(company);
        List<AnimeCompany> companies1=animeCompanyRepository.findAnimeCompanyByOtherNamesContaining(company);
        String res="";
        companies.addAll(companies1);
        res+="查询到以下"+String.valueOf(companies.size())+"个结果\n";
        for(AnimeCompany comp:companies){
            res+="公司 "+comp.getName()+"的别名有";
            List<String> otherNames=comp.getOtherNames();
            for(String othername:otherNames){
                res+=othername+" ";
            }
            res+="\n";
        }
        return res;
    }
    private String getCompanyBirthday(String company){
        List<AnimeCompany> companies = animeCompanyRepository.findAnimeCompanyByNameLike(company);
        List<AnimeCompany> companies1=animeCompanyRepository.findAnimeCompanyByOtherNamesContaining(company);
        String res="";
        companies.addAll(companies1);
        res+="查询到以下"+String.valueOf(companies.size())+"个结果\n";
        for(AnimeCompany comp:companies){
            res+="公司 "+comp.getName()+"的创办日期是";
            res+=comp.getBirthday();
            res+="\n";
        }
        return res;
    }
    private String getCompany(String company){
        List<AnimeCompany> companies = animeCompanyRepository.findAnimeCompanyByNameLike(company);
        List<AnimeCompany> companies1=animeCompanyRepository.findAnimeCompanyByOtherNamesContaining(company);
        String res="";
        companies.addAll(companies1);
        res+="查询到以下"+String.valueOf(companies.size())+"个结果\n";
        for(AnimeCompany comp:companies){
            res+="公司 "+comp.getName()+"的创办日期是";
            res+=comp.getBirthday();
            res+="\n";
            res+="公司的别名有";
            List<String> otherNames=comp.getOtherNames();
            for(String othername:otherNames){
                res+=othername+" ";
            }
            res+="\n";
            res+="公司介绍:"+comp.getDescription()+"\n";
        }
        return res;
    }
    private String getCVCharacters(String cvName){
        String res="";
        List<AnimeCV> cvs=animeCVRepository.findAnimeCVByNameLike(cvName);
        List<AnimeCV> cvs2=animeCVRepository.findAnimeCVByOtherNamesContaining(cvName);
        cvs.addAll(cvs2);
        res+="查询到以下"+String.valueOf(cvs.size())+"个结果\n";
        for(AnimeCV cv:cvs){
            Entity e=entityRepository.findEntityByBgmIdAndType(cv.getCvId(),"AnimeCV");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();

            res+="声优"+cv.getName()+"扮演的角色有以下:"+"\n";
            Set<String> charaList=new HashSet<String>();
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("扮演")){
                    charaList.add(entityRepository.findEntityById(entry.getValue().toString()).getName());
                    //res+=entityRepository.findEntityById(entry.getValue().toString()).getName()+" ";
                }
            }
            for(String s:charaList){
                res+=s+" ";
            }
        }
        return res;
    }
    private String getCVAnimes(String cvName){
        String res="";
        List<AnimeCV> cvs=animeCVRepository.findAnimeCVByNameLike(cvName);
        List<AnimeCV> cvs2=animeCVRepository.findAnimeCVByOtherNamesContaining(cvName);
        cvs.addAll(cvs2);
        res+="查询到以下"+String.valueOf(cvs.size())+"个结果\n";
        for(AnimeCV cv:cvs){
            Entity e=entityRepository.findEntityByBgmIdAndType(cv.getCvId(),"AnimeCV");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();

            res+="声优"+cv.getName()+"配音的动画有以下:"+"\n";
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("配音")){
                    res+=entityRepository.findEntityById(entry.getValue().toString()).getName()+"    ";
                }
            }
        }
        return res;
    }
    private String getIfCVDubCharacter(String cv,String character){

        return "";
    }
    private String getIfCVDubAnime(String cv,String anime){
        return "";
    }
    private String getCharacterAnimes(String character){
        String res="";
        List<AnimeCharacter> charas=animeCharacterRepository.findAnimeCharacterByNameLike(character);
        List<AnimeCharacter> charas2=animeCharacterRepository.findAnimeCharactersByOtherNamesContaining(character);
        charas.addAll(charas2);
        res+="查询到以下"+String.valueOf(charas.size())+"个结果\n";
        for(AnimeCharacter chara:charas){
            Entity e=entityRepository.findEntityByBgmIdAndType(chara.getCharacterId(),"AnimeCharacter");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();
            res+="角色"+chara.getName()+"出演的动画有以下:"+"\n";
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("出演")){
                    res+=entityRepository.findEntityById(entry.getValue().toString()).getName()+"    ";
                }
            }
        }
        return res;
    }
    private String getCharacteCVs(String character){
        String res="";
        List<AnimeCharacter> charas=animeCharacterRepository.findAnimeCharacterByNameLike(character);
        List<AnimeCharacter> charas2=animeCharacterRepository.findAnimeCharactersByOtherNamesContaining(character);
        charas.addAll(charas2);
        res+="查询到以下"+String.valueOf(charas.size())+"个结果\n";
        for(AnimeCharacter chara:charas){
            Entity e=entityRepository.findEntityByBgmIdAndType(chara.getCharacterId(),"AnimeCharacter");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();

            res+="角色"+chara.getName()+"的声优有以下:"+"\n";
            Set<String> cvList=new HashSet<String>();
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("扮演")){
                    cvList.add(entityRepository.findEntityById(entry.getValue().toString()).getName()+" ");
                    //res+=entityRepository.findEntityById(entry.getValue().toString()).getName()+" ";
                }
            }
            for(String s:cvList){
                res+=s+" ";
            }
        }

        return res;
    }
    private String getCompanyAnimes(String company){
        String res="";
        List<AnimeCompany> comps=animeCompanyRepository.findAnimeCompanyByNameLike(company);
        List<AnimeCompany> comps2=animeCompanyRepository.findAnimeCompanyByOtherNamesContaining(company);
        comps.addAll(comps2);
        res+="查询到以下"+String.valueOf(comps.size())+"个结果\n";
        for(AnimeCompany comp:comps){
            Entity e=entityRepository.findEntityByBgmIdAndType(comp.getCompanyId(),"AnimeCompany");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();

            res+="公司"+comp.getName()+"制作的动画有以下:"+"\n";
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("制作")){
                    res+=entityRepository.findEntityById(entry.getValue().toString()).getName()+"    ";
                }
            }
        }
        return res;
    }
    private String getDirectorAnimes(String director){
        String res="";
        List<AnimeDirector> dirs=animeDirectorRepository.findAnimeDirectorByNameLike(director);
        List<AnimeDirector> dirs2=animeDirectorRepository.findAnimeDirectorByOtherNamesContaining(director);
        dirs.addAll(dirs2);
        res+="查询到以下"+String.valueOf(dirs.size())+"个结果\n";
        for(AnimeDirector dir:dirs){
            Entity e=entityRepository.findEntityByBgmIdAndType(dir.getDirectorId(),"AnimeDirector");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();
            res+="导演"+dir.getName()+"执导的动画有以下:"+"\n";
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("导演")){
                    res+=entityRepository.findEntityById(entry.getValue().toString()).getName()+"    ";
                }
            }
            res+="\n";
        }
        return res;
    }
    private String getAnimeCompany(String anime){
        String res="";
        List<Anime> animes=animeRepository.findAnimeByTitleLike(anime);
        List<Anime> animes2=animeRepository.findAnimeByJapaneseNameLike(anime);
        animes.addAll(animes2);
        res+="查询到以下"+String.valueOf(animes.size())+"个结果\n";
        for(Anime ani:animes){
            Entity e=entityRepository.findEntityByBgmIdAndType(ani.getAnimeId(),"Anime");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();
            res+="动画"+ani.getTitle()+"的制作公司是:"+"\n";
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("制作")){
                    res+=entityRepository.findEntityById(entry.getValue().toString()).getName()+"    ";
                }
            }
            res+="\n";
        }
        return res;
    }
    private String getAnimeDirectors(String anime){
        String res="";
        List<Anime> animes=animeRepository.findAnimeByTitleLike(anime);
        List<Anime> animes2=animeRepository.findAnimeByJapaneseNameLike(anime);
        animes.addAll(animes2);
        res+="查询到以下"+String.valueOf(animes.size())+"个结果\n";
        for(Anime ani:animes){
            Entity e=entityRepository.findEntityByBgmIdAndType(ani.getAnimeId(),"Anime");
            Set<Map.Entry<String,String>> entries=e.getRelatesTo().entrySet();
            res+="动画"+ani.getTitle()+"的导演是:"+"\n";
            for(Map.Entry entry:entries){
                if(relationRepository.findRelationById(entry.getKey().toString()).getRelation().equals("导演")){
                    res+=entityRepository.findEntityById(entry.getValue().toString()).getName()+" ";
                }
            }
            res+="\n";
        }
        return res;
    }
    private String getScoreHigherThanXAnime(String score){
        System.out.println(score);
        String res="";
        Double sc=Double.parseDouble(score);
        List<Anime> animes=animeRepository.findAnimeByScoreGreaterThan(sc);
        for(Anime anime:animes){
            res+=anime.getTitle()+" ";
        }
        return res;
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

