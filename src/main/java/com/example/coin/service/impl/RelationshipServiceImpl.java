package com.example.coin.service.impl;

import com.example.coin.DAO.RelationshipRepository;
import com.example.coin.javaBeans.Entity;
import com.example.coin.javaBeans.relationship;
import com.example.coin.service.EntityService;
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
    private EntityService entityService;
    @Autowired
    private RelationshipRepository relationshipRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *  如果实体节点不存在 返回null
     * @param from
     * @param to
     * @param name 关系名称
     * @return 关系节点
     */
    @Override
    public relationship addRelationship(String from, String to, String name) {
        Entity source = entityService.findEntityById(from);
        Entity target = entityService.findEntityById(to);
        if(source == null || target == null) return null;
        relationship rel = new relationship(from, to, name);
        relationshipRepository.save(rel);
        source.getRelatesTo().put(rel.getId(), to);
        target.getRelatesTo().put(rel.getId(), from);
        entityService.updateEntityById(from, source);
        entityService.updateEntityById(to, target);
        return rel;
    }

    /**
     *
     * @param rel
     * @return
     */
    public relationship addRelationship(relationship rel) {
        return relationshipRepository.save(rel);
    }

    /**
     * 首先判断两节点是否存在
     * @param from 被删除的节点关系from的id
     * @param to 被删除的节点关系to的id
     */
    public boolean deleteRelationById(String from, String to, String name) {
        Entity source = entityService.findEntityById(from);
        Entity target = entityService.findEntityById(to);
        if(source == null || target == null) return false;
        //删除关系时，相应的删除节点的relatesTo中的键值对
        Query query = Query.query(Criteria.where("source").is(from).and("target").is(to).and("relation").is(name));
        relationship deletedRel = mongoTemplate.findAndRemove(query, relationship.class, "relationships");
        if(deletedRel == null) return false;
        source.getRelatesTo().remove(deletedRel.getId());
        target.getRelatesTo().remove(deletedRel.getId());
        entityService.updateEntityById(from, source);
        entityService.updateEntityById(to, target);
        return true;
    }

    /**
     *
     * @param id
     */
    @Override
    public boolean deleteRelationById(String id) {
        Optional<relationship> optionalRel = relationshipRepository.findById(id);
        if(!optionalRel.isPresent())
            return false;
        relationship rel = optionalRel.get();
        Entity source = entityService.findEntityById(rel.getSource());
        Entity target = entityService.findEntityById(rel.getTarget());
        source.getRelatesTo().remove(rel.getId());
        target.getRelatesTo().remove(rel.getId());
        entityService.updateEntityById(source.getId(), source);
        entityService.updateEntityById(target.getId(), target);
        relationshipRepository.deleteById(id);
        return true;
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
        rel.setRelation(r.getRelation());
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
        //删除节点中记录的所有关系
        List<Entity> entities = entityService.findAllEntities();
        for(Entity e : entities){
            e.getRelatesTo().clear();
        }
    }
}
