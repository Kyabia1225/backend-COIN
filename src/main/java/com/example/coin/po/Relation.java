package com.example.coin.po;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@ToString(exclude = "id")
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor

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
}
