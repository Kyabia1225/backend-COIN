package com.example.coin.service;

import com.example.coin.javaBeans.relationship;

import java.util.List;

public interface RelationshipService {

    //增加关系
    relationship addRelationship(String from, String to, String name);
    relationship addRelationship(relationship each);
    //靠两个节点id与关系名称删除关系
    boolean deleteRelationById(String source, String target, String name);
    //靠rel的id删除关系
    boolean deleteRelationById(String id);
    //查询关系
    relationship findRelationById(String id);
    //更新关系
    void updateRelationshipById(String id, relationship r);
    //获取所有关系
    List<relationship> findAllRelationships();
    //删除所有关系
    void deleteAllRelationships();
}
