package com.example.coin.controller;

import com.example.coin.DAO.EntityRepository;
import com.example.coin.DAO.RelationRepository;
import com.example.coin.po.Entity;
import com.example.coin.util.RedisUtil;
import com.example.coin.util.ResponseVO;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
        ResponseVO response1 = entityController.addEntity(new Entity("yuzijiang", "person"));
        String id = ((Entity) response1.getContent()).getId();
        Assert.assertTrue(entityController.getEntityById(id).getSuccess());
        entityController.deleteEntityById(id);
        Assert.assertFalse(entityController.getEntityById(id).getSuccess());
        Assert.assertEquals(0, ((List<Entity>)entityController.getEntityList().getContent()).size());
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
