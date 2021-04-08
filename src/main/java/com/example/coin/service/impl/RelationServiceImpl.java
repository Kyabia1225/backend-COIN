package com.example.coin.service.impl;

import com.example.coin.DAO.RelationRepository;
import com.example.coin.po.Entity;
import com.example.coin.po.Relation;
import com.example.coin.service.EntityService;
import com.example.coin.service.RelationService;
import com.example.coin.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.coin.util.RedisUtil.*;

@Service
public class RelationServiceImpl implements RelationService {

    @Autowired
    private EntityService entityService;
    @Autowired
    private RelationRepository relationRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisUtil redisUtil;

    /**
     *  如果实体节点不存在 返回null
     * @param from
     * @param to
     * @param name 关系名称
     * @return 关系节点
     */
    @Override
    public Relation addRelationship(String from, String to, String name) {
        Entity source = entityService.getEntityById(from);
        Entity target = entityService.getEntityById(to);
        if(source == null || target == null) return null;
        //如果entity不为空
        Relation rel = new Relation(from, to, name);
        relationRepository.save(rel);
        source.getRelatesTo().put(rel.getId(), to);
        target.getRelatesTo().put(rel.getId(), from);
        entityService.updateEntityById(from, source, false);
        entityService.updateEntityById(to, target, false);
        //保存到缓存中
        //redisUtil.expire(from, TWO_HOURS_IN_SECOND);
        //redisUtil.expire(to, TWO_HOURS_IN_SECOND);
        redisUtil.set(RELATIONSHIP_REDIS_PREFIX+rel.getId(), rel, TWO_HOURS_IN_SECOND);
        return rel;
    }

    /**
     *
     * @param rel
     * @return
     */
    public Relation addRelationship(Relation rel) {
        redisUtil.set(RELATIONSHIP_REDIS_PREFIX+rel.getId(), rel, TWO_HOURS_IN_SECOND);
        return relationRepository.save(rel);
    }

    /**
     * 首先判断两节点是否存在
     * @param from 被删除的节点关系from的id
     * @param to 被删除的节点关系to的id
     */
    public boolean deleteRelationById(String from, String to, String name) {
        Entity source = entityService.getEntityById(from);
        Entity target = entityService.getEntityById(to);
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
        //redisUtil.expire(from, TWO_HOURS_IN_SECOND);
        //redisUtil.expire(to, TWO_HOURS_IN_SECOND);
        redisUtil.del(RELATIONSHIP_REDIS_PREFIX+deletedRel.getId());
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
        Entity source = entityService.getEntityById(rel.getSource());
        Entity target = entityService.getEntityById(rel.getTarget());
        source.getRelatesTo().remove(rel.getId());
        target.getRelatesTo().remove(rel.getId());
        entityService.updateEntityById(source.getId(), source, false);
        entityService.updateEntityById(target.getId(), target, false);
        //redisUtil.expire(source.getId(), TWO_HOURS_IN_SECOND);
        //redisUtil.expire(target.getId(), TWO_HOURS_IN_SECOND);
        redisUtil.del(RELATIONSHIP_REDIS_PREFIX+id);
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
        Relation relInRedis = (Relation) redisUtil.get(RELATIONSHIP_REDIS_PREFIX + id);
        if(relInRedis!=null){
            redisUtil.expire(RELATIONSHIP_REDIS_PREFIX+id, TWO_HOURS_IN_SECOND);
            return relInRedis;
        }
        //如果没有在redis中找到
        Optional<Relation> optionalRel = relationRepository.findById(id);
        if(optionalRel.isPresent()){
            Relation rel = optionalRel.get();
            redisUtil.set(RELATIONSHIP_REDIS_PREFIX+id, rel, TWO_HOURS_IN_SECOND);
            return rel;
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
    public boolean updateRelationshipById(String id, Relation r) {
        Relation origin = getRelationById(id);
        if(origin == null) return false;
        origin.setRelation(r.getRelation());
        origin.setSource(r.getSource());
        origin.setTarget(r.getTarget());
        relationRepository.save(origin);
        redisUtil.set(RELATIONSHIP_REDIS_PREFIX+id, origin, TWO_HOURS_IN_SECOND);
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
        Set<String> keys = redisUtil.getKeys();
        for(String key : keys){
            if(key.startsWith(RELATIONSHIP_REDIS_PREFIX)){
                redisUtil.del(key);
            }
        }
        //删除节点中记录的所有关系
        List<Entity> entities = entityService.getAllEntities();
        for(Entity e : entities){
            e.getRelatesTo().clear();
            entityService.updateEntityById(e.getId(), e, false);
        }
    }
}
