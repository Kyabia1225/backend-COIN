package com.example.coin.pojo;
import lombok.*;
import org.neo4j.ogm.annotation.*;


@Data
@NoArgsConstructor
@ToString(exclude = "id")
@EqualsAndHashCode
@RequiredArgsConstructor
@NodeEntity(label = "Entity")
public class Entity {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    @NonNull
    private String name;
}
