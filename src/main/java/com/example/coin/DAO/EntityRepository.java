package com.example.coin.DAO;

import com.example.coin.pojo.Entity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface EntityRepository extends Neo4jRepository<Entity, Long> {

}