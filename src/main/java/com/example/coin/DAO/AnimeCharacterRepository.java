package com.example.coin.DAO;

import com.example.coin.po.AnimeCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnimeCharacterRepository extends MongoRepository<AnimeCharacter, String> {
    public AnimeCharacter findAnimeCharacterByNameLike(String name);
    public List<AnimeCharacter> findAnimeCharacterByNameContaining(String name);
    public AnimeCharacter findAnimeCharacterByCharacterId(String characterId);
}
