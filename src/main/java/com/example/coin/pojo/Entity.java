package com.example.coin.pojo;

import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

@NodeEntity(label = "Entity")
public class Entity {
    @Id @GeneratedValue private long id;    //id自增

    @Property(name = "name")
    private String name;

    @Relationship(type = "relationships", direction = Relationship.OUTGOING)
    private Set<Entity> relationships = new HashSet<>();

    private Entity(){
        // Empty constructor required as of Neo4j API 2.0.5
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
