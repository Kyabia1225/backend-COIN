package com.example.coin.DAO;

import com.example.coin.po.Entity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EntityRepository extends MongoRepository<Entity, String> {
    Entity findEntityById(String id);
    List<Entity> findEntitiesByType(String type);
    List<Entity> findEntitiesByName(String name);
}
