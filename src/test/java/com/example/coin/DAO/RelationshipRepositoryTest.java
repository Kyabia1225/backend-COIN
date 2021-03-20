package com.example.coin.DAO;

import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.JVM)

class RelationshipRepositoryTest {
    @Autowired
    RelationshipRepository relationshipRepository;

    @Test
    void saveTest(){
        Entity from = new Entity("from");
        Entity to = new Entity("to");
        relationship rel1 = new relationship(from, to, "connects");
        relationship rel2 = relationshipRepository.save(rel1);
        assertEquals(rel1, rel2);
    }

    @Test
    void findTest(){
        Entity from = new Entity("from");
        Entity to = new Entity("to");
        relationship rel1 = new relationship(from, to, "connects");
        relationshipRepository.save(rel1);
        Long id = rel1.getId();
        relationship rel2 = relationshipRepository.findById(id).get();
        assertEquals(rel1, rel2);
    }

    @Test
    void deleteTest(){
        Entity from = new Entity("from");
        Entity to = new Entity("to");
        relationship rel1 = new relationship(from, to, "connects");
        relationshipRepository.save(rel1);
        Long id = rel1.getId();
        relationshipRepository.deleteById(id);
        assertFalse(relationshipRepository.findById(id).isPresent());

    }

    @Test
    void findAllTest(){
        assertTrue(((List<relationship>)relationshipRepository.findAll()).size()>0);
    }

    @Test
    void deleteAllTest(){
        relationshipRepository.deleteAll();
        assertTrue(((List<relationship>)relationshipRepository.findAll()).size() == 0);
    }

}