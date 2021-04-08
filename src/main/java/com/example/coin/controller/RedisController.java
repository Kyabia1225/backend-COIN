package com.example.coin.controller;


import com.example.coin.po.Entity;
import com.example.coin.po.Relation;
import com.example.coin.service.EntityService;
import com.example.coin.service.RelationService;
import com.example.coin.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.example.coin.util.RedisUtil.*;

@RestController
@RequestMapping("/api/coin")
public class RedisController {
    @Autowired
    private EntityService entityService;
    @Autowired
    private RelationService relationService;
    @Autowired
    private RedisUtil redisUtil;

    //把mongodb中的所有数据初始化到redis中
    public void init(){
        List<Entity> allEntities = entityService.getAllEntities();
        for(Entity e : allEntities){
            redisUtil.set(ENTITY_REDIS_PREFIX+e.getId(), e);
            redisUtil.expire(ENTITY_REDIS_PREFIX+e.getId(), TWO_HOURS_IN_SECOND);
        }
        List<Relation> allRelations = relationService.getAllRelationships();
        for(Relation r : allRelations){
            redisUtil.set(RELATIONSHIP_REDIS_PREFIX+r.getId(), r);
            redisUtil.expire(RELATIONSHIP_REDIS_PREFIX+r.getId(), TWO_HOURS_IN_SECOND);
        }
    }

    //所有redis数据存储到mongodb中（先清空mongodb）
    public void Redis2MongoDB(){
        entityService.deleteAllEntities();
        relationService.deleteAllRelationships();

        Set<String> keys = redisUtil.getKeys();
        for(String key : keys){
            if(key.contains(ENTITY_REDIS_PREFIX)){
                entityService.addEntity((Entity) redisUtil.get(key));
            }
            else{
                relationService.addRelationship((Relation) redisUtil.get(key));
            }
        }

    }

}
