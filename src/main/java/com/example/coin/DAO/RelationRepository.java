package com.example.coin.DAO;

import com.example.coin.po.Relation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RelationRepository extends MongoRepository<Relation, String> {
    Relation findRelationById(String id);
    List<Relation> findRelationBySource(String source);
}
