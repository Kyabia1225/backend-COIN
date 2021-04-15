package com.example.coin.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StringDistanceTest {
    @Test
    public void Test(){
        boolean flag =  StringDistance.matches("huixieng","huixiang");
        Assert.assertTrue(flag);
    }
}