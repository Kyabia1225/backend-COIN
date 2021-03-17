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
    public Entity createEntity(Entity entity) {
        return entityRepository.save(entity);
    }

    @Override
    public void deleteEntityById(Long id) {
        entityRepository.deleteById(id);
    }

    @Override
    public Optional<Entity> findEntityById(Long id) {
        return entityRepository.findById(id);
    }

    @Override
    public List<Entity> findAllEntities() {
        return (List<Entity>)entityRepository.findAll();
    }

    @Override
    public relationship addRelationship(Entity from, Entity to, String name) {
        relationship rel = new relationship(from, to, name);
        return relationshipRepository.save(rel);
    }

    @Override
    public void deleteRelationById(Long fromId, Long toId) {
        relationshipRepository.deleteById(fromId, toId);
    }

    @Override
    public Optional<relationship> findRelationById(Long id) {
        return relationshipRepository.findById(id);
    }

    @Override
    public void deleteAllEntities() {
        entityRepository.deleteAll();
    }

    @Override
    public List<relationship> findAllRelationships() {
        return (List<relationship>) relationshipRepository.findAll();
    }

    @Override
    public void deleteAllRelationships() {
        relationshipRepository.deleteAll();
    }
}
