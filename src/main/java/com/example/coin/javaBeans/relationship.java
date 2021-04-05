package com.example.coin.javaBeans;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@ToString(exclude = "id")
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor

@Document(collection = "relationships")
public class relationship implements Serializable {
    @Id
    private String id;
    @NonNull
    private String source;
    @NonNull
    private String target;
    @NonNull
    private String relation;
}
