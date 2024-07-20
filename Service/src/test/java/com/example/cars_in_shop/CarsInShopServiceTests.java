package com.example.cars_in_shop;

import com.example.exception.NotEnoughSpaceInShopException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.car.Car;
import com.example.car.CarService;
import com.example.shop.Shop;
import com.example.shop.ShopService;
import com.example.storage.BlobStorage;


@ExtendWith(MockitoExtension.class)
public class CarsInShopServiceTests {

    @Mock
    private ShopService shopService;

    @Mock
    private CarService carService;

    @Mock
    private BlobStorage blobStorage;

    @InjectMocks
    private CarsInShopServiceImpl carsInShopService;

    @Test
    void getCarsByShop_ReturnsCarsForShop() {
        // Arrange
        String shopId = "shop1";
        List<String> files = Arrays.asList(
                "shop1_1",
                "shop1_2",
                "shop1_3",
                "shop2_4"
        );

        Car car1 = new Car(1, "Car1", 4, true);
        Car car2 = new Car(2, "Car2", 4, true);
        Car car3 = new Car(3, "Car3", 4, false);

        List<Car> expectedCars = Arrays.asList(car1, car2, car3);

        when(shopService.getById(shopId)).thenReturn(new Shop(shopId, "Shop 1", 10));
        when(blobStorage.listBlobs()).thenReturn(files);
        when(carService.getById(1)).thenReturn(car1);
        when(carService.getById(2)).thenReturn(car2);
        when(carService.getById(3)).thenReturn(car3);

        // Act
        List<Car> actualCars = carsInShopService.getCarsByShop(shopId);

        // Assert
        assertIterableEquals(expectedCars, actualCars);
    }

    @Test
    void addCarToShop_WhenEnoughSpace_SuccessfullyAddsCar() {
        // Arrange
        int carId = 1;
        String shopId = "shop1";

        Shop shop = new Shop(shopId, "Shop 1", 100);
        Car car = new Car(carId, "Car1", 4, true);

        List<String> files = Arrays.asList(
                "shop1_2",
                "shop1_3"
        );

        when(shopService.getById(shopId)).thenReturn(shop);
        when(carService.getById(carId)).thenReturn(car);
        when(blobStorage.listBlobs()).thenReturn(files);
        when(blobStorage.blobExists(shopId + "_" + carId)).thenReturn(false);

        // Act
        Car actualCar = carsInShopService.addCarToShop(carId, shopId);

        // Assert
        assertEquals(car, actualCar);
        verify(blobStorage).uploadBlob(shopId + "_" + carId, new byte[]{});
    }

    @Test
    void addCarToShop_WhenNotEnoughSpace_ThrowsException() {
        // Arrange
        int carId = 1;
        String shopId = "shop1";
        Shop shop = new Shop(shopId, "Shop 1", 1);
        List<String> files = Arrays.asList(
                "shop1_1",
                "shop2_2"
        );

        when(shopService.getById(shopId)).thenReturn(shop);
        when(carService.getById(carId)).thenReturn(new Car(carId, "Car1", 4, true));
        when(blobStorage.listBlobs()).thenReturn(files);

        // Act & Assert
        Exception exception = assertThrows(NotEnoughSpaceInShopException.class, () -> {
            carsInShopService.addCarToShop(carId, shopId);
        });

        assertEquals("Not enough space in shop with id: " + shopId, exception.getMessage());
        verify(blobStorage, never()).uploadBlob(anyString(), any());
    }

    @Test
    void deleteCarFromShop_IfCarExists_DeleteCar() {
        // Arrange
        int carId = 1;
        String shopId = "shop1";
        String fileName = shopId + "_" + carId;

        Shop shop = new Shop(shopId, "Shop 1", 100);
        Car car = new Car(carId, "Car1", 4, true);

        when(shopService.getById(shopId)).thenReturn(shop);
        when(carService.getById(carId)).thenReturn(car);
        when(blobStorage.blobExists(fileName)).thenReturn(true);

        // Act
        carsInShopService.deleteCarFromShop(carId, shopId);

        // Assert
        verify(blobStorage).deleteBlob(fileName);
    }
}

