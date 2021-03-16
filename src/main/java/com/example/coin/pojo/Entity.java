package com.example.coin.pojo;
import org.neo4j.ogm.annotation.*;

import java.util.Objects;


@NodeEntity(label = "Entity")
public class Entity {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    private Entity(){
        // Empty constructor required as of Neo4j API 2.0.5
    }

    public Long getId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
