package com.example.coin.dao;

import com.example.coin.pojo.relationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface RelationshipRepository extends Neo4jRepository<relationship, Long> {
    @Query("MATCH (fromNode) WHERE id(fromNode) = {fromId} MATCH (toNode) WHERE id(toNode) = {toId} MATCH (fromNode)-[r]->(toNode) DELETE r")
    void deleteById(@Param(value="fromId")long fromId, @Param(value="toId")long toId);
}
