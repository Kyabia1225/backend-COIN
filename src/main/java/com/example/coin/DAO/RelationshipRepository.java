package com.example.coin.DAO;

import com.example.coin.javaBeans.relationship;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RelationshipRepository extends MongoRepository<relationship, String> {
}
