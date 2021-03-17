package com.example.coin.pojo;

import org.neo4j.ogm.annotation.*;

import java.util.Objects;

@RelationshipEntity(type = "Relationship")
public class relationship {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Entity from;
    @EndNode
    private Entity to;
    @Property
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Entity getFrom() {
        return from;
    }

    public void setFrom(Entity from) {
        this.from = from;
    }

    public Entity getTo() {
        return to;
    }

    public void setTo(Entity to) {
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public relationship(){

    }

    public relationship(Entity from, Entity to, String name) {
        this.from = from;
        this.to = to;
        this.name = name;
    }

    @Override
    public String toString() {
        return "relaitionship{" +
                "from=" + from +
                ", to=" + to +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        relationship that = (relationship) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
