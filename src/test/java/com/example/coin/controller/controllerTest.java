package com.example.coin.controller;

import com.example.coin.DAO.EntityRepository;
import com.example.coin.DAO.RelationRepository;
import com.example.coin.po.Entity;
import com.example.coin.util.RedisUtil;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class controllerTest {
    @Autowired
    private EntityController entityController;
    @Autowired
    private  RelationController relationController;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private RelationRepository relationRepository;

    @Test
    public void Test01(){

    }

    @Test
    public void Test02(){

    }

    @Test
    public void Test03(){

    }

    @Test
    public void Test04(){

    }

    @Test
    public void Test05(){

    }

    @Test
    public void Test06(){

    }

    @Test
    public void Test07(){

    }

}
