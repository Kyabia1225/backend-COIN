package com.example.coin.controller;

import com.alibaba.fastjson.JSON;
import com.example.coin.pojo.Entity;
import com.example.coin.util.ResponseVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ControllerTest {
    @Autowired
    EntityController entityController;
    @Autowired
    RelationshipController relationshipController;
    @Test
    public void test1() {
        Entity e1 = new Entity("yuzijiang");
        HashMap<String, String> properties = new HashMap<>();
        properties.put("age","1000");
        properties.put("weight","2000");
        properties.put("height","3000");
        e1.setProperties(properties);
        Entity e2 = new Entity("mamama");
        entityController.addEntity(e1);
        entityController.addEntity(e2);
        relationshipController.addRelById(e1.getId(), e2.getId(), "loves");
    }
    //test2是基于test1的测试进行的，自行更改id
    @Test
    public void test2(){
        ResponseVO emm =  entityController.getEntityById("606304b154b0ab6024fac435");
        String s = JSON.toJSONString(emm);
        System.out.println(s);

    }
    @Test
    public void test3(){

    }
}
