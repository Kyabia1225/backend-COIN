package com.example.coin.util;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StringDistanceTest {

    @Test
    public void test1(){
        Assert.assertEquals(1, StringDistance.calculate("hello", "hallo"));
    }

    @Test
    public void test2(){
        Assert.assertTrue(StringDistance.matches("hello", "hallo"));
    }

}