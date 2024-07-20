package com.example.shop;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShopServiceImpl implements ShopService {

    ShopRepository shopRepository;

    @Override
    public List<Shop> getAll() {
        return shopRepository.getAll();
    }

    @Override
    public Shop getById(String id) {
        return getOrElseThrow(id);
    }

    private Shop getOrElseThrow(String id) {
        return shopRepository.getById(id);
    }

    @Override
    public Shop create(Shop shop) {
        return shopRepository.create(shop);
    }

    @Override
    public Shop update(Shop shop) {
        getOrElseThrow(shop.getId());
        return shopRepository.update(shop);
    }

    @Override
    public void deleteById(String id) {
        getOrElseThrow(id);
        shopRepository.deleteById(id);
    }
}