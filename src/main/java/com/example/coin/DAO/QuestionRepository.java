package com.example.coin.DAO;

import com.example.coin.po.Entity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Entity, String>{
}