package com.example.coin.DAO;

import com.example.coin.po.AnimeDirector;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimeDirectorRepository extends MongoRepository<AnimeDirector, String> {
}
