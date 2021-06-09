package com.example.coin.service;

import com.example.coin.po.Relation;
import com.example.coin.vo.RelationVO;

import java.util.List;
import java.util.Set;

public interface RelationService {

    //增加关系
    Relation addRelationship(String from, String to, String name);
    Relation addRelationship(Relation each);
    //靠两个节点id与关系名称删除关系
    boolean deleteRelationById(String source, String target, String name);
    //靠rel的id删除关系
    boolean deleteRelationById(String id);
    //查询关系
    Relation getRelationById(String id);
    //更新关系
    boolean updateRelationshipById(String id, Relation r);
    //获取所有关系
    List<Relation> getAllRelationships();
    //删除所有关系
    void deleteAllRelationships();
    //获取该节点与它的一级节点的所有关系VO实体
    Set<RelationVO> getAssociatedRelations(String id);

    //搜索相关功能
    Set<String> fuzzySearch(String condition);
}
