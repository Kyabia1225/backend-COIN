package com.example.coin.DAO;

import com.example.coin.po.AnimeCompany;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimeCompanyRepository extends MongoRepository<AnimeCompany, String > {
    AnimeCompany findAnimeCompanyByCompanyId(String companyId);
}
