package com.example.coin.service.impl;

import com.example.coin.DAO.RelationshipRepository;
import com.example.coin.javaBeans.relationship;
import com.example.coin.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired
    private RelationshipRepository relationshipRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *
     * @param from
     * @param to
     * @param name 关系名称
     * @return 关系节点
     */
    @Override
    public relationship addRelationship(String from, String to, String name) {
        relationship rel = new relationship(from, to, name);
        return relationshipRepository.save(rel);
    }
    public relationship addRelationship(relationship rel) {
        return relationshipRepository.save(rel);
    }

    /**
     *
     * @param source 被删除的节点关系from的id
     * @param target 被删除的节点关系to的id
     */
    public void deleteRelationById(String source, String target) {
        Query query = Query.query(Criteria.where("source").is(source).and("target").is(target));
        mongoTemplate.remove(query,"relationships");
    }

    /**
     *
     * @param id
     */
    @Override
    public void deleteRelationById(String id) {
        relationshipRepository.deleteById(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public relationship findRelationById(String id) {
        Optional<relationship> optionalRel = relationshipRepository.findById(id);
        if(optionalRel.isPresent()){
            return optionalRel.get();
        }else{
            return null;
        }
    }

    /**
     * 请务必保证id存在
     * @param id
     * @param r
     */
    @Override
    public void updateRelationshipById(String id, relationship r) {
        relationship rel = findRelationById(id);
        rel.setRelationship(r.getRelationship());
        rel.setSource(r.getSource());
        rel.setTarget(r.getTarget());
    }

    /**
     *
     * @return 所有关系的List
     */
    @Override
    public List<relationship> findAllRelationships() {
        return relationshipRepository.findAll();
    }

    /**
     * 删除所有关系
     */
    @Override
    public void deleteAllRelationships() {
        relationshipRepository.deleteAll();
    }
}
