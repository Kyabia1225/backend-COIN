/*
package com.example.coin.DAO;

import com.example.coin.po.Entity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
//请按照顺序一条一条执行， Test方法随机测试顺序
class EntityRepositoryTest {
    @Autowired
    EntityRepository entityRepository;

    @Test
    void Test1(){
        Entity entity1 = new Entity("save");
        Entity entity2 = entityRepository.save(entity1);
        assertEquals(entity1, entity2);
    }

    @Test
    void Test2(){
        List<Entity> entities = entityRepository.findEntitiesByName("save");
        assertEquals(1, entities.size());
    }
    @Test
    void Test3(){
        String id = entityRepository.findEntitiesByName("save").get(0).getId();
        entityRepository.deleteById(id);
        assertEquals(0, entityRepository.findEntitiesByName("save").size());
    }

    @Test
    void Test4(){
        entityRepository.deleteAll();
        assertTrue(((List<Entity>)entityRepository.findAll()).size() == 0);
    }
}
*/
