package com.example.shop;

import java.util.List;

public interface ShopService {
    List<Shop> getAll();

    Shop getById(String id);

    Shop create(Shop shop);

    Shop update(Shop shop);

    void deleteById(String id);
}