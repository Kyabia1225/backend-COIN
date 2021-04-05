package com.example.coin.service;

import com.example.coin.javaBeans.Entity;
import com.example.coin.javaBeans.relationship;

import java.util.List;

public interface EntitySearch {
    //通过名称模糊查询
    List<Entity> fuzzySearchByName(String name);
    //通过类型模糊查询
    List<Entity> fuzzySearchByType (String type);
    //通过属性模糊查询
    List<Entity> fuzzySearchByProperty(String property);
    //通过节点id查询一级相关节点
    List<Entity> associatedEntities(String id);
    //通过节点id查询相关关系
    List<relationship> associatedRelationships(String id);
}
