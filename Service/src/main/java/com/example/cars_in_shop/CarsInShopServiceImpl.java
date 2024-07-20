package com.example.cars_in_shop;

import com.example.car.Car;
import com.example.car.CarService;
import com.example.exception.NotEnoughSpaceInShopException;
import com.example.shop.Shop;
import com.example.shop.ShopService;
import com.example.storage.BlobStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CarsInShopServiceImpl implements CarsInShopService {

    private final ShopService shopService;
    private final CarService carService;

    private final BlobStorage blobStorage;


    @Override
    public List<Car> getCarsByShop(String shopId) {
        shopService.getById(shopId);

        List<String> files = blobStorage.listBlobs();

        return getListOfCarsFromBlobList(files, shopId);
    }

    private boolean isEnoughSpace(Shop shop) {
        int carsInShop = getCarsByShop(shop.getId()).size();
        int placesInShop = shop.getPlacesAmount();
        if (carsInShop < placesInShop) {
            return true;
        }
        throw new NotEnoughSpaceInShopException("Not enough space in shop with id: " + shop.getId());
    }

    @Override
    public Car addCarToShop(int carId, String shopId) {
        Car car = carService.getById(carId);
        Shop shop = shopService.getById(shopId);

        if (isEnoughSpace(shop)) {

            String fileName = shopId + "_" + carId;

            if (!blobStorage.blobExists(fileName)) {
                blobStorage.uploadBlob(fileName, new byte[]{});
            }
        }
        return car;
    }

    @Override
    public void deleteCarFromShop(int carId, String shopId) {
        carService.getById(carId);
        shopService.getById(shopId);

        String fileName = shopId + "_" + carId;

        if (blobStorage.blobExists(fileName)) {
            blobStorage.deleteBlob(fileName);
        }
    }

    private List<Car> getListOfCarsFromBlobList(List<String> files, String shopId) {
        return files.stream()
                .filter(e -> e.startsWith(shopId + "_"))
                .map(e -> e.replace(shopId + "_", ""))
                .map(id -> {
                    try {
                        return Integer.parseInt(id);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(id -> {
                    try {
                        return carService.getById(id);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}

