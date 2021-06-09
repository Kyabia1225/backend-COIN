package com.example.coin.service.impl;

import com.example.coin.DAO.EntityRepository;
import com.example.coin.DAO.RelationRepository;
import com.example.coin.po.Entity;
import com.example.coin.po.Relation;
import com.example.coin.service.EntityService;
import com.example.coin.service.RelationService;
import com.example.coin.util.StringDistance;
import com.example.coin.vo.RelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RelationServiceImpl implements RelationService {

    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private EntityService  entityService;
    @Autowired
    private RelationRepository relationRepository;
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
    public Relation addRelationship(String from, String to, String name) {
        Entity source = entityRepository.findEntityById(from);
        Entity target = entityRepository.findEntityById(to);
        if(source == null || target == null) return null;
        //如果entity不为空
        Relation rel = new Relation(from, to, name);
        relationRepository.save(rel);
        source.getRelatesTo().put(rel.getId(), to);
        target.getRelatesTo().put(rel.getId(), from);
        entityService.updateEntityById(from, source, false);
        entityService.updateEntityById(to, target, false);
        return rel;
    }

    /**
     *
     * @param rel
     * @return
     */
    public Relation addRelationship(Relation rel) {
        Entity source = entityRepository.findEntityById(rel.getSource());
        Entity target = entityRepository.findEntityById(rel.getTarget());
        if(source == null || target == null) return null;
        Relation saved = relationRepository.save(rel);
        source.getRelatesTo().put(rel.getId(), target.getId());
        target.getRelatesTo().put(rel.getId(), source.getId());
        entityService.updateEntityById(source.getId(), source, false);
        entityService.updateEntityById(target.getId(), target, false);
        return saved;
    }

    /**
     * 首先判断两节点是否存在
     * @param from 被删除的节点关系from的id
     * @param to 被删除的节点关系to的id
     */
    public boolean deleteRelationById(String from, String to, String name) {
        Entity source = entityRepository.findEntityById(from);
        Entity target = entityRepository.findEntityById(to);
        if(source == null || target == null) return false;
        //删除关系时，相应的删除节点的relatesTo中的键值对
        //todo: 是否要为source, target, relation建立联合索引？
        Query query = Query.query(Criteria.where("source").is(from).and("target").is(to).and("relation").is(name));
        Relation deletedRel = mongoTemplate.findAndRemove(query, Relation.class, "relationships");
        if(deletedRel == null) return false;
        source.getRelatesTo().remove(deletedRel.getId());
        target.getRelatesTo().remove(deletedRel.getId());
        entityService.updateEntityById(from, source, false);
        entityService.updateEntityById(to, target, false);
        return true;
    }

    /**
     *
     * @param id
     */
    @Override
    public boolean deleteRelationById(String id) {
        Optional<Relation> optionalRel = relationRepository.findById(id);
        if(!optionalRel.isPresent())
            return false;
        Relation rel = optionalRel.get();
        Entity source = entityRepository.findEntityById(rel.getSource());
        Entity target = entityRepository.findEntityById(rel.getTarget());
        source.getRelatesTo().remove(rel.getId());
        target.getRelatesTo().remove(rel.getId());
        entityService.updateEntityById(source.getId(), source, false);
        entityService.updateEntityById(target.getId(), target, false);
        relationRepository.deleteById(id);
        return true;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Relation getRelationById(String id) {
        Optional<Relation> optionalRel = relationRepository.findById(id);
        return optionalRel.orElse(null);
    }

    /**
     * 请务必保证id存在
     * @param id
     * @param r
     */
    @Override
    public boolean updateRelationshipById(String id, Relation r) {
        Relation origin = getRelationById(id);
        if(origin == null) return false;
        origin.setRelation(r.getRelation());
        origin.setSource(r.getSource());
        origin.setTarget(r.getTarget());
        relationRepository.save(origin);
        return true;
    }

    /**
     *
     * @return 所有关系的List
     */
    @Override
    public List<Relation> getAllRelationships() {
        return relationRepository.findAll();
    }

    /**
     * 删除所有关系
     */
    @Override
    public void deleteAllRelationships() {
        relationRepository.deleteAll();
        //删除节点中记录的所有关系
        List<Entity> entities = entityService.getAllEntities();
        for(Entity e : entities){
            e.getRelatesTo().clear();
            entityService.updateEntityById(e.getId(), e, false);
        }
    }

    @Override
    public Set<RelationVO> getAssociatedRelations(String id) {
        Set<RelationVO> relationVOSet = new HashSet<>();
        Entity entity = entityRepository.findEntityById(id);
        List<String> entities = new ArrayList<>();
        entities.add(id);
        entities.addAll(entity.getRelatesTo().values());
        for(String relation:entity.getRelatesTo().keySet()){
            Relation rel = relationRepository.findRelationById(relation);
            if(entities.contains(rel.getSource())&&entities.contains(rel.getTarget())){
                relationVOSet.add(new RelationVO(rel));
            }
        }
        return relationVOSet;
    }

    @Override
    public Set<String> fuzzySearch(String condition) {
        if(condition.isEmpty()) return null;
        Set<String> res = new HashSet<>();
        List<Relation> relations = relationRepository.findAll();
        for(Relation relation:relations){
            if(StringDistance.matches(relation.getRelation(), condition)){
                res.add(relation.getId());
            }
        }
        return res;
    }
}
