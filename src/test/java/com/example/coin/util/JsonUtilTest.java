package com.example.coin.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.Reader;
@SpringBootTest
@RunWith(SpringRunner.class)
public class JsonUtilTest {
    @Test
    public void test01() throws FileNotFoundException {
        Reader reader = new JsonUtil().readJsonFile("D:\\anime_out.json");
        new JsonUtil().analyseAnimeJson(reader);
    }

    @Test
    public void test02() throws FileNotFoundException {
        Reader reader = new JsonUtil().readJsonFile("D:\\character_out");
        new JsonUtil().analyseAnimeCharacterJson(reader);
    }

    @Test
    public void test03() throws FileNotFoundException {
        Reader reader = new JsonUtil().readJsonFile("D:\\company_out");
        new JsonUtil().analyseAnimeCompanyJson(reader);
    }
    @Test
    public void test04() throws FileNotFoundException {
        Reader reader = new JsonUtil().readJsonFile("D:\\cv_out");
        new JsonUtil().analyseAnimeCVJson(reader);
    }
    @Test
    public void test05() throws FileNotFoundException {
        Reader reader = new JsonUtil().readJsonFile("D:\\director_out");
        new JsonUtil().analyseAnimeDirector(reader);
    }
}
