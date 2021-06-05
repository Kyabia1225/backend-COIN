package com.example.coin.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*@SpringBootTest
@RunWith(SpringRunner.class)*/
public class JsonUtilTest {
    @Test
    public void test01() throws FileNotFoundException {
        Reader reader = JsonUtil.readJsonFile("D:\\anime_out.json");
        JsonUtil.analyseAnimeJson(reader);
    }

}
