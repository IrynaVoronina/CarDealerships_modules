package com.example.shop;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopMongoRepository extends MongoRepository<ShopDao, String> {

}
