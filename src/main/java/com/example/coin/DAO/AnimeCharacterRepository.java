package com.example.coin.DAO;

import com.example.coin.po.AnimeCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnimeCharacterRepository extends MongoRepository<AnimeCharacter, String> {
    public List<AnimeCharacter> findAnimeCharacterByNameLike(String name);
    public List<AnimeCharacter> findAnimeCharacterByName(String name);
    public List<AnimeCharacter> findAnimeCharacterByNameContaining(String name);
    public AnimeCharacter findAnimeCharacterByCharacterId(String characterId);
    public List<AnimeCharacter> findAnimeCharactersByOtherNamesContaining(String name);
}
