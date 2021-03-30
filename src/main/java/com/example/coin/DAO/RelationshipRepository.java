package com.example.coin.DAO;

import com.example.coin.pojo.relationship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RelationshipRepository extends MongoRepository<relationship, String> {
    @Query("MATCH (fromNode) WHERE id(fromNode) = {fromId} MATCH (toNode) WHERE id(toNode) = {toId} MATCH (fromNode)-[r]->(toNode) DELETE r")
    void deleteById(@Param(value="fromId")String fromId, @Param(value="toId")String toId);
}
