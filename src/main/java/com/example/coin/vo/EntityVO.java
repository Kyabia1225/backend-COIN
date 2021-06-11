package com.example.coin.vo;

import com.example.coin.po.Entity;

import java.util.Map;
import java.util.Objects;

public class EntityVO {
    private String id;
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
    private Map<String, String> properties;
    //记录与该节点相关的节点与关系: 前者是relationship的id，后者是Entity的id
    private Map<String, String>relatesTo;

    public EntityVO() {
    }

    public EntityVO(Entity e){
        this.id = e.getId();
        this.name = e.getName();
        this.fx = e.getFx();
        this.fy = e.getFy();
        this.x = e.getX();
        this.y = e.getY();
        this.vx = e.getVx();
        this.vy = e.getVy();
        this.type = e.getType();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getFx() {
        return fx;
    }

    public void setFx(Double fx) {
        this.fx = fx;
    }

    public Double getFy() {
        return fy;
    }

    public void setFy(Double fy) {
        this.fy = fy;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getVx() {
        return vx;
    }

    public void setVx(Double vx) {
        this.vx = vx;
    }

    public Double getVy() {
        return vy;
    }

    public void setVy(Double vy) {
        this.vy = vy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, String> getRelatesTo() {
        return relatesTo;
    }

    public void setRelatesTo(Map<String, String> relatesTo) {
        this.relatesTo = relatesTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityVO entityVO = (EntityVO) o;
        return Objects.equals(id, entityVO.id) && Objects.equals(name, entityVO.name) && Objects.equals(fx, entityVO.fx) && Objects.equals(fy, entityVO.fy) && Objects.equals(x, entityVO.x) && Objects.equals(y, entityVO.y) && Objects.equals(vx, entityVO.vx) && Objects.equals(vy, entityVO.vy) && Objects.equals(type, entityVO.type) && Objects.equals(properties, entityVO.properties) && Objects.equals(relatesTo, entityVO.relatesTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, fx, fy, x, y, vx, vy, type, properties, relatesTo);
    }
}
