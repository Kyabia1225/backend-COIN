package com.example.coin.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "relationships")
public class Relation {
    @Id
    private String id;
    private String source;
    private String target;
    private String relation;

    public Relation(String source, String target,  String relation) {
        this.source = source;
        this.target = target;
        this.relation = relation;
    }

    public Relation() {
    }

    public String getId() {
        return this.id;
    }

    public String getSource() {
        return this.source;
    }

    public String getTarget() {
        return this.target;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
