package com.example.coin;

import com.example.coin.controller.EntityController;
import com.example.coin.pojo.Entity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    EntityController entityController;
    @Test
    public void test(){
        Entity e = new Entity("abc");
        entityController.addEntity(e);
        redisTemplate.opsForValue().set("entity1", e);
        Entity e2 = (Entity) redisTemplate.opsForValue().get("entity1");
        System.out.println(e2.getName());
        System.out.println(e2.getId());
    }
}
