package com.example.coin.DAO;

import com.example.coin.po.Entity;
import com.example.coin.po.Relation;
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
public class repositoryTest {
    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private  RelationRepository relationRepository;

    @Test
    public void test01(){
        Entity e = new Entity("huixiang", "person");
        Entity saved = entityRepository.save(e);
        Assert.assertEquals(e, saved);
    }

    @Test
    public void test02(){
        Entity entity = entityRepository.findEntitiesByName("huixiang").get(0);
        Assert.assertNotNull(entity);
    }

    @Test
    public void test03(){
        List<Entity> entities = entityRepository.findEntitiesByName("huixiang");
        int size = entities.size();
        Assert.assertTrue(size>0);
        entityRepository.deleteById(entities.get(0).getId());
        Assert.assertEquals(size, entityRepository.findEntitiesByName("huixiang").size()+1);
    }

    @Test
    public void test04(){
        Entity e1 = entityRepository.save(new Entity("huixiang", "person"));
        Entity e2 = entityRepository.save(new Entity("yuzijiang", "person"));
        Relation friendly = new Relation(e1.getId(), e2.getId(), "friendly to");
        Relation saved = relationRepository.save(friendly);
        Assert.assertEquals(friendly, saved);
    }

    @Test
    public void test05(){
        List<Relation> friendly_to = relationRepository.findRelationsByRelation("friendly to");
        int size = friendly_to.size();
        Assert.assertTrue(size>0);
        relationRepository.deleteById(friendly_to.get(0).getId());
        Assert.assertEquals(size, relationRepository.findRelationsByRelation("friendly to").size()+1);
    }

    @Test
    public void test06(){
        Entity dog = entityRepository.save(new Entity("dog", "animal"));
        Entity cat = entityRepository.save(new Entity("cat", "animal"));
        relationRepository.save(new Relation(dog.getId(), cat.getId(), "stays with"));
        int beforeSize = relationRepository.findAll().size();
        entityRepository.deleteById(dog.getId());
        entityRepository.deleteById(cat.getId());
        Assert.assertEquals(beforeSize, relationRepository.findAll().size());
    }

    @Test
    public void test07(){
        entityRepository.deleteAll();
        relationRepository.deleteAll();
        Assert.assertEquals(0, entityRepository.findAll().size());
        Assert.assertEquals(0, relationRepository.findAll().size());
    }
}
