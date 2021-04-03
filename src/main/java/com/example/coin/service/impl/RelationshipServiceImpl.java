package com.example.coin.service.impl;

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
    public relationship addRelationship(String from, String to, String name) {
        relationship rel = new relationship(from, to, name);
        return relationshipRepository.save(rel);
    }
    public relationship addRelationship(relationship rel) {
        return relationshipRepository.save(rel);
    }

    @Override
    public void deleteRelationById(String fromId, String toId) {
        relationshipRepository.deleteById(fromId, toId);
    }

    @Override
    public void deleteRelationById(String id) {
        relationshipRepository.deleteById(id);
    }

    @Override
    public relationship findRelationById(String id) {
        Optional<relationship> optionalRel = relationshipRepository.findById(id);
        if(optionalRel.isPresent()){
            return optionalRel.get();
        }else{
            return null;
        }
    }

    //使用这个方法请务必保证id存在！
    @Override
    public void updateRelationshipById(String id, relationship r) {
        relationship rel = findRelationById(id);
        rel.setRelationship(r.getRelationship());
        rel.setSource(r.getSource());
        rel.setTarget(r.getTarget());
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
