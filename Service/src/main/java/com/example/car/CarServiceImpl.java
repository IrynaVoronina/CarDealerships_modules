package com.example.car;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public List<Car> getAll() {
        return carRepository.getAll();
    }

    @Override
    public Car getById(int id) {
        return getOrElseThrow(id);
    }

    private Car getOrElseThrow(int id) {
        return carRepository.getById(id);
    }

    @Override
    public Car create(Car car) {
        return carRepository.create(car);
    }

    @Override
    public Car update(Car elementGroup) {
        getOrElseThrow(elementGroup.getId());
        return carRepository.update(elementGroup);
    }

    @Override
    public void deleteById(int id) {
        getOrElseThrow(id);
        carRepository.deleteById(id);
    }
}
