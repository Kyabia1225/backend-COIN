package com.example.coin.pojo;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity
public class relationship {
    @Id @GeneratedValue private long id;    //id自增

    @StartNode
    private Entity from;
    @EndNode
    private Entity to;
    @Property
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

}
