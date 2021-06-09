package com.example.coin.DAO;

import com.example.coin.po.AnimeCV;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimeCVRepository extends MongoRepository<AnimeCV, String> {
    AnimeCV findAnimeCVByNameLike(String cvName);
    AnimeCV findAnimeCVByCvId(String CvId);
}
