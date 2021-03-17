package com.example.coin.service;

import com.example.coin.pojo.*;
import com.example.coin.vo.ResponseVO;

import java.util.List;
import java.util.Optional;

public interface EntityService {
    //增加实体
    ResponseVO createEntity(Entity entity);
    //删除实体
    ResponseVO deleteEntityById(Long id);
    //查询实体
    ResponseVO findEntityById(Long id);
    //获取所有实体
    ResponseVO findAllEntities();
    //删除所有实体
    ResponseVO deleteAllEntities();

    //增加关系
    ResponseVO addRelationship(Entity from, Entity to, String name);
    //删除关系
    ResponseVO deleteRelationById(Long fromId, Long toId);
    //查询关系
    ResponseVO findRelationById(Long id);
    //获取所有关系
    ResponseVO findAllRelationships();
    //删除所有实体
    ResponseVO deleteAllRelationships();
}
