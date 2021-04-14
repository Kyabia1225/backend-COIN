package com.example.coin.controller;

import com.example.coin.po.Entity;
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
    RelationController relationController;
    @Test
    public void test1() {
        Entity e1 = new Entity("yuzijiang");
        HashMap<String, String> properties1 = new HashMap<>();
        properties1.put("age","1000");
        properties1.put("weight","2000");
        properties1.put("height","3000");
        e1.setProperties(properties1);
        Entity e2 = new Entity("mamama");
        Entity e3 = new Entity("huixiang");
        HashMap<String, String> properties3 = new HashMap<>();
        properties3.put("age", "18");
        e3.setProperties(properties3);
        entityController.addEntity(e1);
        entityController.addEntity(e2);
        entityController.addEntity(e3);
        relationController.addRelById(e1.getId(), e2.getId(), "loves");
        relationController.addRelById(e1.getId(), e3.getId(), "friendly");
    }
    //test2是基于test1的测试进行的，自行更改id
    @Test
    public void test2(){
        Entity e = (Entity) entityController.getEntityById("60699207764eee6b502a9862").getContent();
        System.out.println(e);
    }
    @Test
    public void test3(){
        Entity e = new Entity("yuzijiang", "person");
        entityController.addEntity(e);
        Entity newEntity = new Entity("yuzijiang", "person");
        newEntity.getRelatesTo().put("weight","65kg");
        newEntity.getRelatesTo().put("height","171cm");
        entityController.updateEntity(e.getId(), newEntity);
    }

    @Test
    public void test4(){
        Entity a = new Entity("a", "char");
        Entity b = new Entity("b", "char");
        Entity c = new Entity("c", "char");
        Entity d = new Entity("d", "char");
        Entity e = new Entity("e", "char");
        Entity f = new Entity("f", "char");
        Entity g = new Entity("g", "char");
        Entity h = new Entity("h", "char");
        Entity i = new Entity("i", "char");
        entityController.addEntity(a);
        entityController.addEntity(b);
        entityController.addEntity(c);
        entityController.addEntity(d);
        entityController.addEntity(e);
        entityController.addEntity(f);
        entityController.addEntity(g);
        entityController.addEntity(h);
        entityController.addEntity(i);
    }
}
