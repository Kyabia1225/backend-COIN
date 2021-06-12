package com.example.coin.util;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.Reader;
@SpringBootTest
@Ignore
@RunWith(SpringRunner.class)
//通过词条测试把数据录入数据库 不要去除@Ignore
public class JsonUtilTest {
    @Autowired
    JsonUtil jsonUtil;
    @Test
    public void test01() throws FileNotFoundException {
        Reader reader = jsonUtil.readJsonFile("/root/out/anime_out");
        jsonUtil.analyseAnimeJson(reader);
    }
    @Test
    public void test02() throws FileNotFoundException {
        Reader reader = jsonUtil.readJsonFile("/root/out/character_out");
        jsonUtil.analyseAnimeCharacterJson(reader);
    }
    @Test
    public void test03() throws FileNotFoundException {
        Reader reader = jsonUtil.readJsonFile("/root/out/company_out");
        jsonUtil.analyseAnimeCompanyJson(reader);
    }
    @Test
    public void test04() throws FileNotFoundException {
        Reader reader = jsonUtil.readJsonFile("/root/out/cv_out");
       jsonUtil.analyseAnimeCVJson(reader);
    }
    @Test
    public void test05() throws FileNotFoundException {
        Reader reader = jsonUtil.readJsonFile("/root/out/director_out");
        jsonUtil.analyseAnimeDirector(reader);
    }
}
