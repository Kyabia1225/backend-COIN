package com.example.coin.DAO;

import com.example.coin.po.Anime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AnimeRepository extends MongoRepository<Anime, String> {
    Anime findAnimeByAnimeId(String id);
    List<Anime> findAnimeByTitleLike(String title);
    List<Anime> findAnimeByJapaneseNameLike(String title);
    @Query("select * from Anime where score>=${sc}")
    List<Anime> findAnimeByScoreGreaterThan(Double sc);
}
