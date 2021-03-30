package com.example.coin.DAO;

import com.example.coin.pojo.Entity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntityRepository extends MongoRepository<Entity, String> {

}
