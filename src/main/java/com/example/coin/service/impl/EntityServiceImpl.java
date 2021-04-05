package com.example.coin.service.impl;

import com.example.coin.DAO.EntityRepository;
import com.example.coin.javaBeans.Entity;
import com.example.coin.service.EntityService;
import com.example.coin.service.RelationshipService;
import com.example.coin.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
            updateEntityById(correspondingEntityId, correspondingEntity, false);
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
     * 更新指定id的节点
     * @param id    节点id
     * @param e     更新内容
     * @param updateAll     是否全部更新（出于效率问题， 一般只更新relatesTo）
     * @return  是否成功
     */
    @Override
    public boolean updateEntityById(String id, Entity e, boolean updateAll) {
        Entity origin = findEntityById(id);
        if(origin == null) return false;
        if(updateAll) { //出于效率考虑，大多数更新只更新relatesTo，忽略以下变量
            origin.setName(e.getName());
            origin.setType(e.getType());
            origin.setFx(e.getFx());
            origin.setFy(e.getFy());
            origin.setProperties(e.getProperties());
        }
        origin.setRelatesTo(e.getRelatesTo());
        entityRepository.save(origin);
        redisUtil.set(ENTITY_REDIS_PREFIX+id, origin, TWO_HOURS_IN_SECOND);
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
