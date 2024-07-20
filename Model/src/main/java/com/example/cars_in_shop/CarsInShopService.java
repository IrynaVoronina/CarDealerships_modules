package com.example.cars_in_shop;

import com.example.car.Car;

import java.util.List;

public interface CarsInShopService {

    List<Car> getCarsByShop(String shopId);

    Car addCarToShop(int carId, String shopId);

    void deleteCarFromShop(int carId, String shopId);
}
