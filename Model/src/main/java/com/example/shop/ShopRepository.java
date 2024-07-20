package com.example.shop;

import java.util.List;

public interface ShopRepository {
    List<Shop> getAll();

    Shop getById(String id);

    Shop create(Shop shop);

    Shop update(Shop shop);

    void deleteById(String id);
}
