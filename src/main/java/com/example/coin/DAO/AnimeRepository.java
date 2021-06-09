package com.example.coin.DAO;

import com.example.coin.po.Anime;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimeRepository extends MongoRepository<Anime, String> {
    Anime findAnimeByAnimeId(String id);
}
