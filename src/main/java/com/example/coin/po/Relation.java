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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Relation)) return false;
        final Relation other = (Relation) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$source = this.getSource();
        final Object other$source = other.getSource();
        if (this$source == null ? other$source != null : !this$source.equals(other$source)) return false;
        final Object this$target = this.getTarget();
        final Object other$target = other.getTarget();
        if (this$target == null ? other$target != null : !this$target.equals(other$target)) return false;
        final Object this$relation = this.getRelation();
        final Object other$relation = other.getRelation();
        if (this$relation == null ? other$relation != null : !this$relation.equals(other$relation)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Relation;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $source = this.getSource();
        result = result * PRIME + ($source == null ? 43 : $source.hashCode());
        final Object $target = this.getTarget();
        result = result * PRIME + ($target == null ? 43 : $target.hashCode());
        final Object $relation = this.getRelation();
        result = result * PRIME + ($relation == null ? 43 : $relation.hashCode());
        return result;
    }

    public String toString() {
        return "Relation(source=" + this.getSource() + ", target=" + this.getTarget() + ", relation=" + this.getRelation() + ")";
    }
}
