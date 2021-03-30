package com.example.coin.service.impl;

import com.example.coin.DAO.EntityRepository;
import com.example.coin.pojo.Entity;
import com.example.coin.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntityServiceImpl implements EntityService {
    @Autowired
    private EntityRepository entityRepository;

    @Override
    public Entity createEntity(Entity entity) {
        return entityRepository.save(entity);
    }

    @Override
    public void deleteEntityById(String id) {
        entityRepository.deleteById(id);
    }

    @Override
    public Entity findEntityById(String id) {
        Optional<Entity>optionalEntity =  entityRepository.findById(id);
        if(optionalEntity.isPresent()){
            return optionalEntity.get();
        }
        else return null;
    }

    //使用这个方法请务必保证id存在！
    @Override
    public void updateEntityById(String id, Entity e) {
        Entity entity = findEntityById(id);
        entity.setName(e.getName());
        entity.setProperties(e.getProperties());
        entity.setType(e.getType());
    }

    @Override
    public List<Entity> findAllEntities() {
        return (List<Entity>) entityRepository.findAll();
    }

    @Override
    public void deleteAllEntities() {
        entityRepository.deleteAll();
    }

}
