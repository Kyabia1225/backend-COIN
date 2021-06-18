package com.example.coin.DAO;

import com.example.coin.po.AnimeCompany;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnimeCompanyRepository extends MongoRepository<AnimeCompany, String > {
    AnimeCompany findAnimeCompanyByCompanyId(String companyId);
    List<AnimeCompany> findAnimeCompanyByNameLike(String name);
    List<AnimeCompany> findAnimeCompanyByOtherNamesContaining(String name);

}
