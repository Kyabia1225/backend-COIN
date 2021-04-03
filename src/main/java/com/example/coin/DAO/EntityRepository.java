package com.example.coin.DAO;

import com.example.coin.javaBeans.Entity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntityRepository extends MongoRepository<Entity, String> {

}
