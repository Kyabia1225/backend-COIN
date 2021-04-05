package com.example.coin.service.impl;

import com.example.coin.javaBeans.Entity;
import com.example.coin.javaBeans.relationship;
import com.example.coin.service.EntitySearch;

import java.util.List;

public class EntitySearchImpl implements EntitySearch {
    @Override
    public List<Entity> fuzzySearchByName(String name) {
        
        return null;
    }

    @Override
    public List<Entity> fuzzySearchByType(String type) {
        return null;
    }


    @Override
    public List<Entity> fuzzySearchByProperty(String property) {
        return null;
    }

    @Override
    public List<Entity> associatedEntities(String id) {
        return null;
    }

    @Override
    public List<relationship> associatedRelationships(String id) {
        return null;
    }
}
