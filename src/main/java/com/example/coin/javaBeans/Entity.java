package com.example.coin.javaBeans;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String name;
    //fx, fy用来存储节点位置
    private Double fx;
    private Double fy;
    //节点属性（键值对形式）
    private HashMap<String, String> properties;
    private String type;
    //记录与该节点相关的节点与关系: 前者是relationship的id，后者是Entity的id
    private HashMap<String, String>relatesTo;

    public HashSet<String> associatedEntites(){
        HashSet<String> entitiesId= new HashSet<>();
        for(String e : relatesTo.values()){
            entitiesId.add(e);
        }
        return entitiesId;
    }
    
    public HashSet<String> associatedRelationships(){
        HashSet<String> relsId = new HashSet<>();
        for(String rel : relatesTo.keySet()){
            relsId.add(rel);
        }
        return relsId;
    }

    public Entity(String name) {
        this.name = name;
        this.relatesTo = new HashMap<>();
        this.properties = new HashMap<>();
    }
}
