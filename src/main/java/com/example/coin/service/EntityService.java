package com.example.coin.service;

import com.example.coin.pojo.*;
import com.example.coin.vo.ResponseVO;

import java.util.List;

public interface EntityService {
    //增加实体
    Entity createEntity(Entity entity);
    //删除实体
    void deleteEntityById(Long id);
    //查询实体
    Entity findEntityById(Long id);
    //获取所有实体
    List<Entity> findAllEntities();
    //删除所有实体
    void deleteAllEntities();

    //增加关系
    relationship addRelationship(Entity from, Entity to, String name);
    //删除关系
    void deleteRelationById(Long fromId, Long toId);
    //查询关系
    relationship findRelationById(Long id);
    //获取所有关系
    List<relationship> findAllRelationships();
    //删除所有实体
    void deleteAllRelationships();
}
