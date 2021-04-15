package com.example.coin.service.impl;

import com.example.coin.service.EntityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.coin.service.RelationService;

import java.util.Set;


@SpringBootTest
@RunWith(SpringRunner.class)
public class EntityServiceImplTest {
    @Autowired
    private EntityService entityService;
    @Autowired
    private RelationService relationService;
    @Test
    public void Test1(){
        long before = System.currentTimeMillis();
        Set<String> set = entityService.fuzzySearch("yuziji");
        for(String a:set){
            System.out.println(entityService.getEntityById(a).getName());
        }
        long after = System.currentTimeMillis();
        System.out.println(after - before);
    }

}