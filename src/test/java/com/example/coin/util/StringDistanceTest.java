package com.example.coin.util;

import com.example.coin.po.Entity;
import com.example.coin.vo.EntityVO;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
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
        Entity e = new Entity();
        e.setName("aaa");
        e.setType("b");
        e.setBgmId("123");
        e.setFx(1.2d);
        HashMap<String, String> map = new HashMap<>();
        map.put("1","2");
        e.setRelatesTo(map);
        Set<EntityVO> entityVOS = new HashSet<>();
        entityVOS.add(new EntityVO(e));
        entityVOS.add(new EntityVO(e));
        System.out.println(entityVOS.size());
    }

}