package com.example.coin.pojo;

import lombok.*;
import org.neo4j.ogm.annotation.*;

@Data
@ToString(exclude = "id")
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@RelationshipEntity(type = "Relationship")
public class relationship {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    @NonNull
    private Entity from;
    @EndNode
    @NonNull
    private Entity to;
    @Property
    @NonNull
    private String name;
}
