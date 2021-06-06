package com.example.coin.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.HashMap;

@Document(collection = "entities")
public class Entity implements Serializable {
    @Id
    private String id;
    @org.springframework.data.redis.core.index.Indexed
    @org.springframework.data.mongodb.core.index.Indexed
    private String name;
    //位置信息
    private Double fx;
    private Double fy;
    private Double x;
    private Double y;
    private Double vx;
    private Double vy;

    private String type;
    //节点属性（键值对形式）
    private HashMap<String, String> properties;
    //记录与该节点相关的节点与关系: 前者是relationship的id，后者是Entity的id
    private HashMap<String, String>relatesTo;

    String bgmId;   //bangumi上的id

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

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Double getFx() {
        return this.fx;
    }

    public Double getFy() {
        return this.fy;
    }

    public Double getX() {
        return this.x;
    }

    public Double getY() {
        return this.y;
    }

    public Double getVx() {
        return this.vx;
    }

    public Double getVy() {
        return this.vy;
    }

    public String getType() {
        return this.type;
    }

    public HashMap<String, String> getProperties() {
        return this.properties;
    }

    public HashMap<String, String> getRelatesTo() {
        return this.relatesTo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFx(Double fx) {
        this.fx = fx;
    }

    public void setFy(Double fy) {
        this.fy = fy;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setVx(Double vx) {
        this.vx = vx;
    }

    public void setVy(Double vy) {
        this.vy = vy;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }

    public void setRelatesTo(HashMap<String, String> relatesTo) {
        this.relatesTo = relatesTo;
    }

    public String getBgmId() {
        return bgmId;
    }

    public void setBgmId(String bgmId) {
        this.bgmId = bgmId;
    }
}
