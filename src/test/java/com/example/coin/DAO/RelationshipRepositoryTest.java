/*
package com.example.coin.DAO;

import com.example.coin.po.Entity;
import com.example.coin.po.Relation;
import org.junit.jupiter.api.Test;
import org.junit.runner.OrderWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class RelationRepositoryTest {
    @Autowired
    RelationRepository relationRepository;
    @Autowired
    EntityRepository entityRepository;
    @Test
    void saveTest(){
        Entity from = new Entity("from");
        Entity to = new Entity("to");
        entityRepository.save(from);
        entityRepository.save(to);
        Relation rel = new Relation(from.getId(), to.getId(), "connects");
        relationRepository.save(rel);
    }

    @Test
    void findTest(){
        assertEquals(1, relationRepository.findRelationsByRelation("connects").size());
    }

    @Test
    void deleteTest(){
        String id = relationRepository.findRelationsByRelation("connects").get(0).getId();
        relationRepository.deleteById(id);
        assertEquals(0, relationRepository.findRelationsByRelation("connects").size());
    }


    @Test
    void deleteAllTest(){
        relationRepository.deleteAll();
        assertTrue(((List<Relation>) relationRepository.findAll()).size() == 0);
    }

}
*/
