package com.example.coin.service;

import com.example.coin.pojo.*;
import java.util.List;
import java.util.Optional;

public interface EntityService {
    //增加实体
    Entity create(Entity entity);
    //删除实体
    void deleteById(long id);
    //查询
    Optional<Entity> findById(long id);
    //获取所有实体
    List<Entity>findAll();
    //增加关系
    relationship addRelationship(Entity from, Entity to, String name);
    void deleteRelationById(long fromId, long toId);
}
