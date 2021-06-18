package com.example.coin.DAO;

import com.example.coin.po.AnimeCV;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnimeCVRepository extends MongoRepository<AnimeCV, String> {
    AnimeCV findAnimeCVByCvId(String CvId);
    List<AnimeCV> findAnimeCVByNameLike(String name);
}
