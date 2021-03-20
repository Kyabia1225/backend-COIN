package com.example.coin.DAO;

import com.example.coin.pojo.Entity;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.JVM)
class EntityRepositoryTest {
    @Autowired
    EntityRepository entityRepository;

    @Test
    void saveTest(){
        Entity entity1 = new Entity("save");
        Entity entity2 = entityRepository.save(entity1);
        assertEquals(entity1, entity2);
    }

    @Test
    void findTest(){
        Entity entity1 = new Entity("find");
        entityRepository.save(entity1);
        Optional<Entity>optionalEntity = entityRepository.findById(entity1.getId());
        Entity entity2 = optionalEntity.get();
        assertEquals(entity1, entity2);
    }
    @Test
    void deleteTest(){
        Entity entity1 = new Entity("delete");
        entityRepository.save(entity1);
        Long id = entity1.getId();
        entityRepository.deleteById(id);
        assertFalse(entityRepository.findById(id).isPresent());
    }

    @Test
    void findAllTest(){
        assertTrue(((List<Entity>)entityRepository.findAll()).size()>0);
    }

    @Test
    void deleteAllTest(){
        entityRepository.deleteAll();
        assertTrue(((List<Entity>)entityRepository.findAll()).size() == 0);
    }
}