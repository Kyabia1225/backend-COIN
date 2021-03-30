package com.example.coin.service;

import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;

import java.util.List;

public interface RelationshipService {

    //增加关系
    relationship addRelationship(String from, String to, String name);
    relationship addRelationship(relationship each);
    //删除关系
    void deleteRelationById(String fromId, String toId);
    //查询关系
    relationship findRelationById(String id);
    //更新关系
    void updateRelationshipById(String id, relationship r);
    //获取所有关系
    List<relationship> findAllRelationships();
    //删除所有关系
    void deleteAllRelationships();
}
