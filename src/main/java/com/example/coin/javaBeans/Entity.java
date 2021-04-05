package com.example.coin.javaBeans;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

@Data
@ToString(exclude = "id")
@EqualsAndHashCode

@Document(collection = "entities")
public class Entity implements Serializable {
    @Id
    private String id;
    @org.springframework.data.redis.core.index.Indexed
    @org.springframework.data.mongodb.core.index.Indexed
    @Field("name")
    private String name;
    //fx, fy用来存储节点位置
    private Double fx;
    private Double fy;
    @Field("type")
    private String type;
    //节点属性（键值对形式）
    private HashMap<String, String> properties;
    //记录与该节点相关的节点与关系: 前者是relationship的id，后者是Entity的id
    private HashMap<String, String>relatesTo;

    public Entity(){
        this.relatesTo = new HashMap<>();
        this.properties = new HashMap<>();
    }
    
    public Entity(String name) {
        this.name = name;
        this.relatesTo = new HashMap<>();
        this.properties = new HashMap<>();
    }
    public Entity(String name, String type){
        this.name = name;
        this.type = type;
        this.relatesTo = new HashMap<>();
        this.properties = new HashMap<>();
    }

    public HashSet<String> associatedEntites(){
        return new HashSet<>(relatesTo.values());
    }

    public HashSet<String> associatedRelationships(){
        return new HashSet<>(relatesTo.keySet());
    }
}
