package com.example.car;

import java.util.List;

public interface CarService {
    List<Car> getAll();

    Car getById(int id);

    Car create(Car car);

    Car update(Car car);

    void deleteById(int id);

}