package com.example.coin.impl;

import com.example.coin.dao.EntityRepository;
import com.example.coin.dao.RelationshipRepository;
import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;
import com.example.coin.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntityServiceImpl implements EntityService {
    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private RelationshipRepository relationshipRepository;

    @Override
    public Entity create(Entity entity) {
        return entityRepository.save(entity);
    }

    @Override
    public void deleteById(long id) {
        entityRepository.deleteById(id);
    }

    @Override
    public Optional<Entity> findById(long id) {
        return entityRepository.findById(id);
    }

    @Override
    public List<Entity> findAll() {
        return (List<Entity>)entityRepository.findAll();
    }

    @Override
    public relationship addRelationship(Entity from, Entity to, String name) {
        relationship rel = new relationship(from, to, name);
        return relationshipRepository.save(rel);
    }

    @Override
    public void deleteRelationById(long fromId, long toId) {
        relationshipRepository.deleteById(fromId, toId);
    }

    @Override
    public void deleteAll() {
        entityRepository.deleteAll();
    }
}
