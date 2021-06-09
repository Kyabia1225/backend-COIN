package com.example.coin.vo;

import com.example.coin.po.Relation;

public class RelationVO {
    private String id;
    private String source;
    private String target;
    private String relation;

    public RelationVO() {
    }

    public RelationVO(Relation relation){
        this.id = relation.getId();
        this.source = relation.getSource();
        this.target = relation.getTarget();
        this.relation = relation.getRelation();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
