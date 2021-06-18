package com.example.coin.DAO;

import com.example.coin.po.AnimeDirector;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnimeDirectorRepository extends MongoRepository<AnimeDirector, String> {
    AnimeDirector findAnimeDirectorByDirectorId(String directorId);
    List<AnimeDirector> findAnimeDirectorByNameLike(String name);
    List<AnimeDirector> findAnimeDirectorByOtherNamesContaining(String name);
}
