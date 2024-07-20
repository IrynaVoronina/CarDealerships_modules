package com.example.shop;

import exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ShopRepositoryImpl implements ShopRepository {

    private final ShopMongoRepository mongoRepository;
    private final ShopDaoMapper daoMapper;

    @Override
    public List<Shop> getAll() {
        return daoMapper.mapToModelList(mongoRepository.findAll());
    }

    @Override
    public Shop getById(String id) {
        Optional<ShopDao> dao = mongoRepository.findById(id);
        if (dao.isPresent()) {
            return daoMapper.mapToModel(dao.get());
        } else {
            throw new ResourceNotFoundException("Shop with id " + id + " not found");
        }
    }

    @Override
    public Shop create(Shop shop) {
        ShopDao dao = daoMapper.mapToDao(shop);
        ShopDao saved = mongoRepository.save(dao);
        return daoMapper.mapToModel(saved);
    }

    @Override
    public Shop update(Shop shop) {
        ShopDao dao = daoMapper.mapToDao(shop);
        ShopDao updated = mongoRepository.save(dao);
        return daoMapper.mapToModel(updated);
    }

    @Override
    public void deleteById(String id) {
        mongoRepository.deleteById(id);
    }
}
