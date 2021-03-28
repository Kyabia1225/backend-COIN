package com.example.coin.impl;

import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;
import com.example.coin.service.EntityService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)



class EntityServiceTest {
    @Autowired
    private EntityService entityService;

    @Test
    void createEntity() {
        Entity entity1 = new Entity("testCreate");
        Entity entity2 = entityService.createEntity(entity1);
        assertEquals(entity1, entity2);
    }

    @Test
    void findEntityById() {
        Entity entity1 = new Entity("testFind");
        Entity entity2 = entityService.createEntity(entity1);
        Entity entity3 = entityService.findEntityById(entity2.getId());
        assertEquals(entity1, entity3);
    }

    @Test
    void deleteEntityById() {
        Entity entity1 = new Entity("testDelete");
        Entity entity2 = entityService.createEntity(entity1);
        Long id = entity2.getId();
        entityService.deleteEntityById(id);
        assertNull(entityService.findEntityById(id));
    }



    @Test
    void findAllEntities() {
        assertTrue(entityService.findAllEntities().size()>0);
    }

    @Test
    void addRelationship() {
        Entity entity1 = new Entity("from");
        Entity entity2 = new Entity("to");
        relationship rel = entityService.addRelationship(entity1, entity2, "connects");
        assertNotNull(rel);
    }

    @Test
    void findRelationById() {
        Entity entity1 = new Entity("from");
        Entity entity2 = new Entity("to");
        relationship rel = entityService.addRelationship(entity1, entity2, "connects");
        Long relId = rel.getId();
        assertNotNull(entityService.findRelationById(relId));
    }

    @Test
    void deleteRelationById() {
        Entity entity1 = new Entity("from");
        Entity entity2 = new Entity("to");
        relationship rel = entityService.addRelationship(entity1, entity2, "connects");
        Long relId = rel.getId();
        entityService.deleteRelationById(entity1.getId(), entity2.getId());
        assertNull(entityService.findRelationById(relId));
    }



    @Test
    void deleteAllEntities() {
        entityService.deleteAllEntities();
        assertEquals(0, entityService.findAllEntities().size());
    }

    @Test
    void findAllRelationships() {
        Entity entity1 = new Entity("from");
        Entity entity2 = new Entity("to");
        entityService.addRelationship(entity1, entity2, "connects");
        assertTrue(entityService.findAllRelationships().size()>0);
    }

    @Test
    void deleteAllRelationships() {
        entityService.deleteAllRelationships();
        assertEquals(0, entityService.findAllRelationships().size());
    }
}