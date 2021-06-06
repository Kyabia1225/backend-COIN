package com.example.coin.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.Reader;
public class JsonUtilTest {
    @Test
    public void test01() throws FileNotFoundException {
        Reader reader = JsonUtil.readJsonFile("D:\\anime_out.json");
        JsonUtil.analyseAnimeJson(reader);
    }

    @Test
    public void test02() throws FileNotFoundException {
        Reader reader = JsonUtil.readJsonFile("D:\\character_out");
        JsonUtil.analyseAnimeCharacterJson(reader);
    }

    @Test
    public void test03() throws FileNotFoundException {
        Reader reader = JsonUtil.readJsonFile("D:\\company_out");
        JsonUtil.analyseAnimeCompanyJson(reader);
    }
    @Test
    public void test04() throws FileNotFoundException {
        Reader reader = JsonUtil.readJsonFile("D:\\cv_out");
        JsonUtil.analyseAnimeCVJson(reader);
    }
    @Test
    public void test05() throws FileNotFoundException {
        Reader reader = JsonUtil.readJsonFile("D:\\director_out");
        JsonUtil.analyseAnimeDirector(reader);
    }
}
