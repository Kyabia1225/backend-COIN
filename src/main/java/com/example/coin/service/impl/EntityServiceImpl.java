package com.example.coin.service.impl;

import com.example.coin.DAO.EntityRepository;
import com.example.coin.javaBeans.Entity;
import com.example.coin.service.EntityService;
import com.example.coin.service.RelationshipService;
import com.example.coin.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.example.coin.util.RedisUtil.*;

@Service
public class EntityServiceImpl implements EntityService {
    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private RelationshipService relationshipService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 将关系节点持久化到数据库中
     * @param entity 关系节点
     * @return
     */
    @Override
    public Entity createEntity(Entity entity) {
        Entity e = entityRepository.save(entity);
        redisUtil.set(ENTITY_REDIS_PREFIX+e.getId(), e, TWO_HOURS_IN_SECOND);
        return e;
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    public boolean deleteEntityById(String id) {
        Entity entity = findEntityById(id);
        if(entity == null) return false;
        //删除与节点相关的所有关系、与该节点有关的节点中relatesTo的记录
        HashSet<String> associatedRelationships = entity.associatedRelationships();
        for(String relId : associatedRelationships){
            //根据这个关系节点找到对应关系后，删除对方实体节点中关于本实体节点的信息
            String correspondingEntityId = entity.getRelatesTo().get(relId);
            Entity correspondingEntity = findEntityById(correspondingEntityId);
            correspondingEntity.getRelatesTo().remove(relId);
            updateEntityById(correspondingEntityId, correspondingEntity);
            //redisUtil.expire(ENTITY_REDIS_PREFIX+correspondingEntityId, TWO_HOURS_IN_SECOND);//update方法中redis重新设置了过期时间
            //然后删除这个关系
            redisUtil.del(RELATIONSHIP_REDIS_PREFIX+relId);
            relationshipService.deleteRelationById(relId);
        }
        redisUtil.del(ENTITY_REDIS_PREFIX+id);
        entityRepository.deleteById(id);
        return true;
    }

    /**
     * 根据id寻找
     * @param id
     * @return
     */
    @Override
    public Entity findEntityById(String id) {
        Entity entityInRedis = (Entity)redisUtil.get(ENTITY_REDIS_PREFIX + id);
        if(entityInRedis!=null) {
            redisUtil.expire(ENTITY_REDIS_PREFIX+id, TWO_HOURS_IN_SECOND);
            return entityInRedis;
        }
        //如果在redis中没有查询到
        Optional<Entity>optionalEntity =  entityRepository.findById(id);
        if(!optionalEntity.isPresent()) return null;
        else{
            Entity e = optionalEntity.get();
            redisUtil.set(ENTITY_REDIS_PREFIX+id, e, TWO_HOURS_IN_SECOND);
            return e;
        }
    }

    /**
     * 更新指定id的节点，请务必保证id存在
     * @param id
     * @param e
     */
    @Override
    public boolean updateEntityById(String id, Entity e) {
        if(findEntityById(id) == null) return false;
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("name", e.getName());
        update.set("fx", e.getFx());
        update.set("fy", e.getFy());
        update.set("properties", e.getProperties());
        update.set("type", e.getType());
        update.set("relatesTo", e.getRelatesTo());
        mongoTemplate.updateFirst(query, update, Entity.class, "entities");
        /*
            现在遇到了一个问题，当使用MongoTemplate进行update操作的时候，mongoTemplate并不会给我返还新的Entity的信息
            我要更新redis中的Entity又必须首先根据id去find这个Entity，然而调用find方法的时候会首先从redis中去查询，造成了矛盾。
            暂时解决办法：update操作的时候，删除redis中这个节点，迫使下次find这个节点的时候从mongodb中读取，从而更新redis
         */
        redisUtil.del(ENTITY_REDIS_PREFIX+id);
        return true;
    }

    /**
     * 返回所有实体节点
     * @return
     */
    @Override
    public List<Entity> findAllEntities() {
        return entityRepository.findAll();
    }

    /**
     * 删除所有实体节点
     */
    @Override
    public void deleteAllEntities() {
        entityRepository.deleteAll();
        //删除所有实体节点的同时删除所有关系节点
        relationshipService.deleteAllRelationships();
        //释放所有缓存
        redisUtil.flushdb();
    }

}
