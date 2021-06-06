package com.example.coin.service.impl;

import com.example.coin.DAO.EntityRepository;
import com.example.coin.po.Entity;
import com.example.coin.service.EntityService;
import com.example.coin.service.RelationService;
import com.example.coin.util.RedisUtil;
import com.example.coin.util.ResponseVO;
import com.example.coin.util.StringDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.coin.util.RedisUtil.*;

@Service
public class EntityServiceImpl implements EntityService {
    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private RelationService relationService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 将关系节点持久化到数据库中
     * @param entity 关系节点
     * @return
     */
    @Override
    public Entity addEntity(Entity entity) {
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
        Entity entity = getEntityById(id);
        if(entity == null) return false;
        //删除与节点相关的所有关系、与该节点有关的节点中relatesTo的记录
        Set<String> associatedRelationships = getAssociatedRelations(entity.getId());
        for(String relId : associatedRelationships){
            //根据这个关系节点找到对应关系后，删除对方实体节点中关于本实体节点的信息
            String correspondingEntityId = entity.getRelatesTo().get(relId);
            Entity correspondingEntity = getEntityById(correspondingEntityId);
            correspondingEntity.getRelatesTo().remove(relId);
            updateEntityById(correspondingEntityId, correspondingEntity, false);
            //redisUtil.expire(ENTITY_REDIS_PREFIX+correspondingEntityId, TWO_HOURS_IN_SECOND);//update方法中redis重新设置了过期时间
            //然后删除这个关系
            redisUtil.del(RELATIONSHIP_REDIS_PREFIX+relId);
            relationService.deleteRelationById(relId);
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
    public Entity getEntityById(String id) {
        /*Entity entityInRedis = (Entity)redisUtil.get(ENTITY_REDIS_PREFIX + id);
        if(entityInRedis!=null) {
            redisUtil.expire(ENTITY_REDIS_PREFIX+id, TWO_HOURS_IN_SECOND);
            return entityInRedis;
        }
        //如果在redis中没有查询到*/
        if(id == null) return null;
        Optional<Entity>optionalEntity =  entityRepository.findById(id);
        if(!optionalEntity.isPresent()) return null;
        else{
            Entity e = optionalEntity.get();
            //redisUtil.set(ENTITY_REDIS_PREFIX+id, e, TWO_HOURS_IN_SECOND);
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
        Entity origin = getEntityById(id);
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
        //redisUtil.set(ENTITY_REDIS_PREFIX+id, origin, TWO_HOURS_IN_SECOND);
        return true;
    }

    /**
     * 返回所有实体节点
     * @return
     */
    @Override
    public List<Entity> getAllEntities() {
        return entityRepository.findAll();
    }

    /**
     * 删除所有实体节点
     */
    @Override
    public void deleteAllEntities() {
        entityRepository.deleteAll();
        //删除所有实体节点的同时删除所有关系节点
        relationService.deleteAllRelationships();
        //释放所有缓存
        redisUtil.flushdb();
    }

    @Override
    public Set<String> getAssociatedRelations(String id) {
        Entity entity = entityRepository.findEntityById(id);
        if(entity == null) return null;
        return new HashSet<>(entity.getRelatesTo().keySet());
    }

    @Override
    public Set<String> getAssociatedEntities(String id) {
        Entity entity = entityRepository.findEntityById(id);
        if(entity == null) return null;
        return new HashSet<>(entity.getRelatesTo().values());
    }

    @Override
    public ResponseVO updateLocations(List<Entity> entities) {
        for(Entity before:entities){
            Entity after = entityRepository.findEntityById(before.getId());
            after.setX(before.getX());
            after.setY(before.getY());
            after.setFx(before.getFx());
            after.setFy(before.getFy());
            after.setVx(before.getVx());
            after.setVy(before.getVy());
            entityRepository.save(after);
            redisUtil.set(ENTITY_REDIS_PREFIX+after.getId(), after);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public Set<String> fuzzySearch(String condition) {
        if (condition == null) return null;
        Set<String> res = new HashSet<>(); //长度>=3
        List<Entity> entities = entityRepository.findAll();
        for (Entity entity : entities) {
            String id = entity.getId();
            //名字距离
            if (StringDistance.matches(entity.getName(), condition)) {
                res.add(id);
                continue;
            }
            //类型距离
            else if (StringDistance.matches(entity.getType(), condition)) {
                res.add(id);
                continue;
            }

            boolean keyFlag = false;
            //属性距离
            for (String key : entity.getProperties().keySet()) {
                if (StringDistance.matches(key, condition)) {
                    res.add(id);
                    keyFlag = true;
                    break;
                }
            }
            if (keyFlag) continue;
            for (String value : entity.getProperties().values()) {
                if (StringDistance.matches(value, condition)) {
                    res.add(id);
                    break;
                }
            }
        }
        return res;
    }
}
