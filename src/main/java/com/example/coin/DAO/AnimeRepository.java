package com.example.coin.DAO;

import com.example.coin.po.Anime;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnimeRepository extends MongoRepository<Anime, String> {
    Anime findAnimeByAnimeId(String id);
    List<Anime> findAnimeByTitleLike(String title);
}
