package com.example.coin.service.impl;

import com.example.coin.DAO.EntityRepository;
import com.example.coin.javaBeans.Entity;
import com.example.coin.service.EntityService;
import com.example.coin.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class EntityServiceImpl implements EntityService {
    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private RelationshipService relationshipService;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 将关系节点持久化到数据库中
     * @param entity 关系节点
     * @return
     */
    @Override
    public Entity createEntity(Entity entity) {
        return entityRepository.save(entity);
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
            //然后删除这个关系
            relationshipService.deleteRelationById(relId);
        }

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
        Optional<Entity>optionalEntity =  entityRepository.findById(id);
        return optionalEntity.orElse(null);
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
        mongoTemplate.upsert(query, update, "entities");
        return true;
    }

    /**
     * 返回所有实体节点
     * @return
     */
    @Override
    public List<Entity> findAllEntities() {
        return (List<Entity>) entityRepository.findAll();
    }

    /**
     * 删除所有实体节点
     */
    @Override
    public void deleteAllEntities() {
        entityRepository.deleteAll();
        //删除所有实体节点的同时删除所有关系节点
        relationshipService.deleteAllRelationships();
    }

}
