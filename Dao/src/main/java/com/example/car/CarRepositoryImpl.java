package com.example.car;

import exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarRepositoryImpl implements CarRepository {

    private final CarJpaRepository jpaRepository;
    private final CarDaoMapper daoMapper;

    @Override
    public List<Car> getAll() {
        return daoMapper.mapToModelList(jpaRepository.findAll());
    }

    @Override
    public Car getById(int id) {
        Optional<CarDao> dao = jpaRepository.findById(id);
        if (dao.isPresent()) {
            return daoMapper.mapToModel(dao.get());
        } else {
            throw new ResourceNotFoundException("Car with id " + id + " not found");
        }
    }

    @Override
    public Car create(Car car) {
        CarDao dao = daoMapper.mapToDao(car);
        CarDao saved = jpaRepository.save(dao);
        return daoMapper.mapToModel(saved);
    }

    @Override
    public Car update(Car car) {
        CarDao dao = daoMapper.mapToDao(car);
        CarDao updated = jpaRepository.save(dao);
        return daoMapper.mapToModel(updated);
    }

    @Override
    public void deleteById(int id) {
        jpaRepository.deleteById(id);
    }
}
