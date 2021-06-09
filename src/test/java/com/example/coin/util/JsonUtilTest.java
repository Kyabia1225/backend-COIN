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
public class JsonUtilTest {
    @Autowired
    JsonUtil jsonUtil;
    @Ignore
    @Test
    public void test01() throws FileNotFoundException {
        Reader reader = jsonUtil.readJsonFile("D:\\anime_out");
        jsonUtil.analyseAnimeJson(reader);
    }
    @Ignore
    @Test
    public void test02() throws FileNotFoundException {
        Reader reader = jsonUtil.readJsonFile("D:\\character_out");
        jsonUtil.analyseAnimeCharacterJson(reader);
    }
    @Ignore
    @Test
    public void test03() throws FileNotFoundException {
        Reader reader = jsonUtil.readJsonFile("D:\\company_out");
        jsonUtil.analyseAnimeCompanyJson(reader);
    }
    @Ignore
    @Test
    public void test04() throws FileNotFoundException {
        Reader reader = jsonUtil.readJsonFile("D:\\cv_out");
       jsonUtil.analyseAnimeCVJson(reader);
    }
    @Ignore
    @Test
    public void test05() throws FileNotFoundException {
        Reader reader = jsonUtil.readJsonFile("D:\\director_out");
        jsonUtil.analyseAnimeDirector(reader);
    }
}
