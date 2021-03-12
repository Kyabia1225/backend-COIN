package com.example.coin.dao;

import com.example.coin.pojo.relationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RelationshipRepository extends Neo4jRepository<relationship, Long> {

    void deleteById(long fromId, long toId);
}
