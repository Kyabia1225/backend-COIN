package com.example.coin.util;

import com.example.coin.po.Entity;
import com.example.coin.vo.EntityVO;
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

    @Test
    public void test3(){
        System.out.println(new JsonUtil().dateAddress("？？？？-12-25"));
        System.out.println(new JsonUtil().dateAddress("1998年12月25日"));
        System.out.println(new JsonUtil().dateAddress("2月25日"));
    }

}