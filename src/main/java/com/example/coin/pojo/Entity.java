package com.example.coin.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.HashMap;

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
    //todo: 需要记录和该节点有关的节点与关系
}
