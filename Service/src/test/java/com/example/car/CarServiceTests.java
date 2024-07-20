package com.example.car;

import exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTests {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;


    @Test
    void getAll_ReturnAllCars() {
        // Arrange
        List<Car> expectedCars = Arrays.asList(
                new Car(1, "Car1", 4, true),
                new Car(2, "Car2", 4, true),
                new Car(3, "Car3", 4, false)
        );
        when(carRepository.getAll()).thenReturn(expectedCars);

        // Act
        List<Car> actualCars = carService.getAll();

        // Assert
        assertEquals(expectedCars, actualCars);
    }


    @Test
    void getById_IfExists_ReturnCar() {
        // Arrange
        int id = 100;
        Car expectedCar = new Car(id, "Car", 4, true);

        when(carRepository.getById(id)).thenReturn(expectedCar);

        // Act
        Car actualCar = carService.getById(id);

        // Assert
        assertEquals(expectedCar, actualCar);
    }


    @Test
    void getById_IfAbsent_ThrowsResourceNotFoundException() {
        // Arrange
        int nonExistingId = 999;
        when(carRepository.getById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            carService.getById(nonExistingId);
        });
    }

    @Test
    void create_ReturnsCreatedCar() {
        // Arrange
        Car car = new Car(1, "Car", 4, true);

        when(carRepository.create(any(Car.class))).thenReturn(car);

        // Act
        Car createdCar = carService.create(car);

        // Assert
        assertEquals(car, createdCar);
    }


    @Test
    void update_ReturnsUpdatedCar() {
        // Arrange
        int id = 1;
        Car car = new Car(id, "updated", 4, true);

        when(carRepository.update(any(Car.class))).thenReturn(car);

        // Act
        Car update = carService.update(car);

        // Assert
        assertEquals(car, update);
    }

    @Test
    void deleteById_IfCarExists_DeleteCar() {
        // Arrange
        int id = 1;

        // Act
        carService.deleteById(id);

        // Assert
        verify(carRepository, times(1)).getById(id);
        verify(carRepository, times(1)).deleteById(id);
    }
}