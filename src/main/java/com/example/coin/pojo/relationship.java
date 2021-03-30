package com.example.coin.pojo;

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
public class relationship implements Serializable {
    @Id
    private String id;
    @NonNull
    private String from;
    @NonNull
    private String to;
    @NonNull
    private String relationship;
}
