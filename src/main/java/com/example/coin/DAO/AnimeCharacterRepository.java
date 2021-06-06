package com.example.coin.DAO;

import com.example.coin.po.AnimeCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimeCharacterRepository extends MongoRepository<AnimeCharacter, String> {
    //public AnimeCharacter findAnimeCharacterByCharacterId(String characterId);
}
