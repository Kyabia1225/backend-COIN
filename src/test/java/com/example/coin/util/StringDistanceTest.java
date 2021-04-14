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
        int calculate = StringDistance.calculate("huixiang","ca");
        System.out.println(calculate);
    }
}