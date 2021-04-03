package com.example.coin.service.impl;

import com.example.coin.DAO.EntityRepository;
import com.example.coin.javaBeans.Entity;
import com.example.coin.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntityServiceImpl implements EntityService {
    @Autowired
    private EntityRepository entityRepository;
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
    public void deleteEntityById(String id) {
        entityRepository.deleteById(id);
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
    }

}
