package com.example.coin.impl;

import com.example.coin.DAO.EntityRepository;
import com.example.coin.DAO.RelationshipRepository;
import com.example.coin.pojo.Entity;
import com.example.coin.pojo.relationship;
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
    public void deleteEntityById(Long id) {
        entityRepository.deleteById(id);
    }

    @Override
    public Entity findEntityById(Long id) {
        Optional<Entity>optionalEntity =  entityRepository.findById(id);
        if(optionalEntity.isPresent()){
            return optionalEntity.get();
        }
        else return null;
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
