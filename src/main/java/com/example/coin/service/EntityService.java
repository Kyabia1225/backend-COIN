package com.example.coin.service;

import com.example.coin.pojo.*;

import java.util.List;

public interface EntityService {
    //增加实体
    Entity createEntity(Entity entity);
    //删除实体
    void deleteEntityById(String id);
    //查询实体
    Entity findEntityById(String id);
    //更新实体
    void updateEntityById(String id, Entity e);
    //获取所有实体
    List<Entity> findAllEntities();
    //删除所有实体
    void deleteAllEntities();

}
