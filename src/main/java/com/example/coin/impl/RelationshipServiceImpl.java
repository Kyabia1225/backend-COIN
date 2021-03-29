package com.example.coin.impl;

import com.example.coin.DAO.RelationshipRepository;
import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;
import com.example.coin.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired
    private RelationshipRepository relationshipRepository;

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
    public relationship findRelationById(Long id) {
        Optional<relationship> optionalRel = relationshipRepository.findById(id);
        if(optionalRel.isPresent()){
            return optionalRel.get();
        }else{
            return null;
        }
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
