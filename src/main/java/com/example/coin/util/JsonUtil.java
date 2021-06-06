package com.example.coin.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.example.coin.po.Anime;
import com.example.coin.po.AnimeCharacter;
import com.example.coin.po.Entity;

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
                String addressedDate;
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
            //转化成节点
            Entity e = new Entity();
            e.setType("Anime");
            e.setBgmId(anime.getAnimeId());
            e.setProperties(null);
            e.setName(anime.getTitle());
            //todo: 保存节点进数据库
        }
        jsonArray.endArray();
        jsonArray.close();
    }

    public static void analyseAnimeCharacterJson(Reader reader){
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
            character.setBirthday((String) jsonOne.get("birthday"));
            Entity e = new Entity();
            e.setType("AnimeCharacter");
            e.setBgmId(character.getCharacterId());
            e.setProperties(null);
            e.setName(character.getName());
            //todo: 保存节点进数据库
        }
        jsonArray.endArray();
        jsonArray.close();
    }
}
