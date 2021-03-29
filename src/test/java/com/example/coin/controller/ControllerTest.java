package com.example.coin.controller;

import com.example.coin.pojo.*;
import com.example.coin.util.ResponseVO;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)

public class ControllerTest {
    @Autowired
    EntityController entityController;
    @Test
    public void test(){
        Entity e1 = new Entity("xyl");
        Entity e2 = new Entity("myn");
        entityController.addEntity(e1);
        entityController.addEntity(e2);
        Object e3 = entityController.getEntityById(e1.getId()).getContent();
        Object e4 = entityController.getEntityById(e2.getId()).getContent();
        System.out.println(((Entity) e3).getName());
        System.out.println(((Entity) e4).getName());
        entityController.getEntityList();
    }
}
