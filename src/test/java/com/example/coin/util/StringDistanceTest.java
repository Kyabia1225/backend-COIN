package com.example.coin.util;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
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