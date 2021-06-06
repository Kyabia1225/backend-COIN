package com.example.coin.po;

import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "relationships")
public class Relation implements Serializable {
    @Id
    private String id;
    @NonNull
    private String source;
    @NonNull
    private String target;
    @NonNull
    @org.springframework.data.redis.core.index.Indexed
    @org.springframework.data.mongodb.core.index.Indexed
    private String relation;

    public Relation(@NonNull String source, @NonNull String target, @NonNull String relation) {
        this.source = source;
        this.target = target;
        this.relation = relation;
    }

    public Relation() {
    }

    public String getId() {
        return this.id;
    }

    public @NonNull String getSource() {
        return this.source;
    }

    public @NonNull String getTarget() {
        return this.target;
    }

    public @NonNull String getRelation() {
        return this.relation;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSource(@NonNull String source) {
        this.source = source;
    }

    public void setTarget(@NonNull String target) {
        this.target = target;
    }

    public void setRelation(@NonNull String relation) {
        this.relation = relation;
    }
}
