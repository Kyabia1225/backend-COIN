package com.example.coin.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.example.coin.DAO.*;
import com.example.coin.po.*;
import com.example.coin.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JsonUtil {
    @Autowired
    EntityRepository entityRepository;
    @Autowired
    RelationRepository relationRepository;
    @Autowired
    AnimeRepository animeRepository;
    @Autowired
    AnimeCompanyRepository animeCompanyRepository;
    @Autowired
    AnimeCharacterRepository animeCharacterRepository;
    @Autowired
    AnimeCVRepository animeCVRepository;
    @Autowired
    AnimeDirectorRepository animeDirectorRepository;
    @Autowired
    RelationService relationService;

    public Reader readJsonFile(String fileName)throws FileNotFoundException {
        return new FileReader(fileName);
    }

    public void analyseAnimeJson(Reader reader){
        JSONReader jsonArray = new JSONReader(reader);
        jsonArray.startArray();
        while (jsonArray.hasNext()){
            JSONObject jsonOne = (JSONObject)jsonArray.readObject();
            Anime anime = new Anime();
            anime.setAnimeId((String) jsonOne.get("anime_id"));
            anime.setJapaneseName((String) jsonOne.get("JapaneseName"));
            anime.setTitle((String) jsonOne.get("title"));
            anime.setLength((String) jsonOne.get("length"));
            //日期需要统一格式，如果多个日期选择最早的
            String date = (String)  jsonOne.get("startDate");
            anime.setStartDate(dateAddress(date));
            //导演
            anime.setDirector((String) jsonOne.get("director"));
            //导演id
            anime.setDirectorId((String) jsonOne.get("director_id"));
            //制作公司
            anime.setCompany((String) jsonOne.get("company"));
            //制作公司id
            anime.setCompanyId((String) jsonOne.get("company_id"));
            //评分
            anime.setScore(Double.parseDouble((String) jsonOne.get("score")));
            //排名
            anime.setRanking(Integer.parseInt((String) jsonOne.get("ranking")));
            //动画描述
            anime.setDescription((String) jsonOne.get("description"));
            //处理角色列表
            JSONArray characters = (JSONArray) jsonOne.get("characters");
            List<String> characterList = new ArrayList<>(characters.size());
            List<String> cvEntityList = new ArrayList<>(characters.size());
            List<String> characterEntityList = new ArrayList<>(characters.size());
            for (Object tmp : characters) {
                String characterId = (String)((JSONObject) tmp).get("character_id");
                String cvId = (String)((JSONObject) tmp).get("CV_id");
                Entity characterEntity = entityRepository.findEntityByBgmIdAndType(characterId, "AnimeCharacter");
                Entity cvEntity = entityRepository.findEntityByBgmIdAndType(cvId, "AnimeCV");
                //建立角色和cv的关系
                if(characterEntity!=null&&cvEntity!=null) {
                    relationService.addRelationship(cvEntity.getId(), characterEntity.getId(), "扮演");
                    characterList.add(characterId);
                    cvEntityList.add(cvEntity.getId());
                    characterEntityList.add(characterEntity.getId());
                }
            }
            anime.setCharacterList(characterList);
            animeRepository.save(anime);
            //转化成节点
            Entity e = new Entity();
            e.setType("Anime");
            e.setBgmId(anime.getAnimeId());
            e.setProperties(null);
            e.setName(anime.getTitle());
            Entity animeEntity = entityRepository.save(e);
            //建立cv、角色、导演、公司和动画的联系
            for(String cvEntityId:cvEntityList){
                relationService.addRelationship(cvEntityId, animeEntity.getId(), "配音");
            }
            for(String characterEntityId:characterEntityList){
                relationService.addRelationship(characterEntityId, animeEntity.getId(), "出演");
            }
            Entity animeDirector = entityRepository.findEntityByBgmIdAndType(anime.getDirectorId(), "AnimeDirector");
            if(animeDirector!=null)
                relationService.addRelationship(animeDirector.getId(), animeEntity.getId(), "导演");
            Entity animeCompany = entityRepository.findEntityByBgmIdAndType(anime.getCompanyId(), "AnimeCompany");
            if(animeCompany!=null)
                relationService.addRelationship(animeCompany.getId(), animeEntity.getId(), "制作");
        }
        jsonArray.endArray();
        jsonArray.close();
    }

    public void analyseAnimeCharacterJson(Reader reader){
        JSONReader jsonArray = new JSONReader(reader);
        jsonArray.startArray();
        while (jsonArray.hasNext()) {
            JSONObject jsonOne = (JSONObject) jsonArray.readObject();
            AnimeCharacter character = new AnimeCharacter();
            character.setCharacterId((String) jsonOne.get("character_id"));
            character.setName((String) jsonOne.get("name"));
            character.setGender((String) jsonOne.get("gender"));
            JSONArray otherNames = (JSONArray) jsonOne.get("other_names");
            List<String> otherNameList = new ArrayList<>(otherNames.size());
            for(Object tmp : otherNames){
                otherNameList.add((String) tmp);
            }
            character.setOtherNames(otherNameList);
            character.setDescription((String) jsonOne.get("description"));
            character.setBirthday(dateAddress((String) jsonOne.get("birthday")));
            animeCharacterRepository.save(character);
            Entity e = new Entity();
            e.setType("AnimeCharacter");
            e.setBgmId(character.getCharacterId());
            e.setProperties(null);
            e.setName(character.getName());
            entityRepository.save(e);
        }
        jsonArray.endArray();
        jsonArray.close();
    }

    public void analyseAnimeCompanyJson(Reader reader){
        JSONReader jsonArray = new JSONReader(reader);
        jsonArray.startArray();
        while (jsonArray.hasNext()) {
            JSONObject jsonOne = (JSONObject) jsonArray.readObject();
            AnimeCompany company = new AnimeCompany();
            company.setCompanyId((String) jsonOne.get("company_id"));
            company.setName((String) jsonOne.get("name"));
            company.setProfession((String) jsonOne.get("profession"));
            company.setDescription((String) jsonOne.get("description"));
            company.setBirthday(dateAddress((String)  jsonOne.get("birthday")));
            JSONArray otherNames = (JSONArray) jsonOne.get("other_names");
            List<String> otherNameList = new ArrayList<>(otherNames.size());
            for(Object tmp : otherNames){
                otherNameList.add((String) tmp);
            }
            company.setOtherNames(otherNameList);
            animeCompanyRepository.save(company);
            Entity e = new Entity();
            e.setType("AnimeCompany");
            e.setBgmId(company.getCompanyId());
            e.setProperties(null);
            e.setName(company.getName());
            entityRepository.save(e);
        }
        jsonArray.endArray();
        jsonArray.close();
    }

    public void analyseAnimeCVJson(Reader reader){
        JSONReader jsonArray = new JSONReader(reader);
        jsonArray.startArray();
        while (jsonArray.hasNext()) {
            JSONObject jsonOne = (JSONObject) jsonArray.readObject();
            AnimeCV animeCV = new AnimeCV();
            animeCV.setCvId((String) jsonOne.get("cv_id"));
            animeCV.setName((String) jsonOne.get("name"));
            animeCV.setGender((String) jsonOne.get("gender"));
            animeCV.setProfession((String) jsonOne.get("profession"));
            animeCV.setDescription((String) jsonOne.get("description"));
            animeCV.setBirthday(dateAddress((String) jsonOne.get("birthday")));
            JSONArray otherNames = (JSONArray) jsonOne.get("other_names");
            List<String> otherNameList = new ArrayList<>(otherNames.size());
            for(Object tmp : otherNames){
                otherNameList.add((String) tmp);
            }
            animeCV.setOtherNames(otherNameList);
            animeCVRepository.save(animeCV);
            Entity e = new Entity();
            e.setType("AnimeCV");
            e.setBgmId(animeCV.getCvId());
            e.setProperties(null);
            e.setName(animeCV.getName());
            entityRepository.save(e);
        }
        jsonArray.endArray();
        jsonArray.close();
    }

    public void analyseAnimeDirector(Reader reader){
        JSONReader jsonArray = new JSONReader(reader);
        jsonArray.startArray();
        while (jsonArray.hasNext()) {
            JSONObject jsonOne = (JSONObject) jsonArray.readObject();
            AnimeDirector animeDirector = new AnimeDirector();
            animeDirector.setDirectorId((String) jsonOne.get("director_id"));
            animeDirector.setName((String) jsonOne.get("name"));
            animeDirector.setGender((String) jsonOne.get("gender"));
            animeDirector.setProfession((String) jsonOne.get("profession"));
            animeDirector.setDescription((String) jsonOne.get("description"));
            animeDirector.setBirthday(dateAddress((String) jsonOne.get("birthday")));
            JSONArray otherNames = (JSONArray) jsonOne.get("other_names");
            List<String> otherNameList = new ArrayList<>(otherNames.size());
            for(Object tmp : otherNames){
                otherNameList.add((String) tmp);
            }
            animeDirector.setOtherNames(otherNameList);
            animeDirectorRepository.save(animeDirector);
            Entity e = new Entity();
            e.setType("AnimeDirector");
            e.setBgmId(animeDirector.getDirectorId());
            e.setProperties(null);
            e.setName(animeDirector.getName());
            entityRepository.save(e);
        }
        jsonArray.endArray();
        jsonArray.close();
    }

    public String dateAddress(String date){
        Pattern pattern = Pattern.compile("\\d{4}年\\d{1,2}月\\d{1,2}日|\\d{4}-\\d{1,2}-\\d{1,2}");
        Matcher matcher = pattern.matcher(date);
        String addressedDate;
        if(matcher.find()){
            addressedDate = matcher.group().replace("年", "-").replace("月", "-").replace("日", "");
        }else{
            addressedDate = null;
        }
        return addressedDate;
    }
}

