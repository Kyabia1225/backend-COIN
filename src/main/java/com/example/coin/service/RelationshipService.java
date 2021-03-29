package com.example.coin.service;

import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;

import java.util.List;

public interface RelationshipService {

    //增加关系
    relationship addRelationship(Entity from, Entity to, String name);
    //删除关系
    void deleteRelationById(Long fromId, Long toId);
    //查询关系
    relationship findRelationById(Long id);
    //获取所有关系
    List<relationship> findAllRelationships();
    //删除所有关系
    void deleteAllRelationships();
}
