package com.example.coin.service;

import com.example.coin.javaBeans.relationship;

import java.util.List;

public interface RelationshipSearch {
    //通过名称查询关系
    List<relationship> fuzzySearchByRelation(String relation);
}
