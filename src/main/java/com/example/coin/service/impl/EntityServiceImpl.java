package com.example.coin.service.impl;

import com.example.coin.DAO.EntityRepository;
import com.example.coin.javaBeans.Entity;
import com.example.coin.service.EntityService;
import com.example.coin.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
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
        //删除与节点相关的所有关系
        HashSet<String> associatedRelationships = entity.associatedRelationships();
        for(String relsId : associatedRelationships){
            relationshipService.deleteRelationById(relsId);
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
        if(optionalEntity.isPresent()){
            return optionalEntity.get();
        }
        else return null;
    }

    /**
     * 更新指定id的节点，请务必保证id存在
     * @param id
     * @param e
     */
    @Override
    public void updateEntityById(String id, Entity e) {
        Entity entity = findEntityById(id);
        entity.setName(e.getName());
        entity.setProperties(e.getProperties());
        entity.setType(e.getType());
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
