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
@NoArgsConstructor
@RequiredArgsConstructor

@Document(collection = "entities")
public class Entity implements Serializable {
    @Id
    private String id;
    @NonNull
    private String name;
    //fx, fy用来存储节点位置
    private Double fx;
    private Double fy;
    //节点属性（键值对形式）
    private HashMap<String, String> properties;
    private String type;
    //记录与该节点相关的节点与关系
    private HashMap<relationship, Entity>relatesTo;

    public HashSet<String> associatedEntites(){
        HashSet<String> entitiesId= new HashSet<>();
        for(Entity e : relatesTo.values()){
            entitiesId.add(e.getId());
        }
        return entitiesId;
    }
    
    public HashSet<String> associatedRelationships(){
        HashSet<String> relsId = new HashSet<>();
        for(relationship rel : relatesTo.keySet()){
            relsId.add(rel.getId());
        }
        return relsId;
    }
}
