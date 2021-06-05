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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Entity)) return false;
        final Entity other = (Entity) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$fx = this.getFx();
        final Object other$fx = other.getFx();
        if (this$fx == null ? other$fx != null : !this$fx.equals(other$fx)) return false;
        final Object this$fy = this.getFy();
        final Object other$fy = other.getFy();
        if (this$fy == null ? other$fy != null : !this$fy.equals(other$fy)) return false;
        final Object this$x = this.getX();
        final Object other$x = other.getX();
        if (this$x == null ? other$x != null : !this$x.equals(other$x)) return false;
        final Object this$y = this.getY();
        final Object other$y = other.getY();
        if (this$y == null ? other$y != null : !this$y.equals(other$y)) return false;
        final Object this$vx = this.getVx();
        final Object other$vx = other.getVx();
        if (this$vx == null ? other$vx != null : !this$vx.equals(other$vx)) return false;
        final Object this$vy = this.getVy();
        final Object other$vy = other.getVy();
        if (this$vy == null ? other$vy != null : !this$vy.equals(other$vy)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$properties = this.getProperties();
        final Object other$properties = other.getProperties();
        if (this$properties == null ? other$properties != null : !this$properties.equals(other$properties))
            return false;
        final Object this$relatesTo = this.getRelatesTo();
        final Object other$relatesTo = other.getRelatesTo();
        if (this$relatesTo == null ? other$relatesTo != null : !this$relatesTo.equals(other$relatesTo)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Entity;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $fx = this.getFx();
        result = result * PRIME + ($fx == null ? 43 : $fx.hashCode());
        final Object $fy = this.getFy();
        result = result * PRIME + ($fy == null ? 43 : $fy.hashCode());
        final Object $x = this.getX();
        result = result * PRIME + ($x == null ? 43 : $x.hashCode());
        final Object $y = this.getY();
        result = result * PRIME + ($y == null ? 43 : $y.hashCode());
        final Object $vx = this.getVx();
        result = result * PRIME + ($vx == null ? 43 : $vx.hashCode());
        final Object $vy = this.getVy();
        result = result * PRIME + ($vy == null ? 43 : $vy.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $properties = this.getProperties();
        result = result * PRIME + ($properties == null ? 43 : $properties.hashCode());
        final Object $relatesTo = this.getRelatesTo();
        result = result * PRIME + ($relatesTo == null ? 43 : $relatesTo.hashCode());
        return result;
    }

    public String toString() {
        return "Entity(name=" + this.getName() + ", fx=" + this.getFx() + ", fy=" + this.getFy() + ", x=" + this.getX() + ", y=" + this.getY() + ", vx=" + this.getVx() + ", vy=" + this.getVy() + ", type=" + this.getType() + ", properties=" + this.getProperties() + ", relatesTo=" + this.getRelatesTo() + ")";
    }
}
