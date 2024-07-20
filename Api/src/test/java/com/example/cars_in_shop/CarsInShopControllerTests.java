package com.example.cars_in_shop;

import com.example.car.Car;
import com.example.car.CarServiceMapper;
import com.example.car.dto.CarResponseDto;
import com.example.controllers.cars_in_shop.CarsInShopController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarsInShopController.class)
public class CarsInShopControllerTests {
    private final String path = "/api/v1/shops";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarsInShopService carsInShopService;

    @MockBean
    private CarServiceMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void get_IsShopExists_ReturnsAllCars() throws Exception {
        // Arrange
        String shopId = "664f5816c7a56c41976302c8";

        List<Car> cars = Arrays.asList(
                new Car(1, "Car1", 4, true),
                new Car(2, "Car2", 4, false),
                new Car(3, "Car3", 4, false));

        List<CarResponseDto> dtoList = Arrays.asList(
                new CarResponseDto(1, "Car1", 4, true),
                new CarResponseDto(2, "Car2", 4, false),
                new CarResponseDto(3, "Car3", 4, false));

        when(carsInShopService.getCarsByShop(shopId)).thenReturn(cars);
        when(mapper.mapToDtoList(cars)).thenReturn(dtoList);

        // Act
        mockMvc.perform(get(path + "/{shopId}/cars", shopId)
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().json(objectMapper.writeValueAsString(dtoList)));
    }

    @Test
    void post_IfPlacesEnough_ReturnAddedCar() throws Exception {

        // Arrange
        int carId = 1;
        String shopId = "664f5816c7a56c41976302c8";

        Car car = new Car(carId, "Car1", 4, true);
        CarResponseDto dto = new CarResponseDto(carId, "Car1", 4, true);

        when(carsInShopService.addCarToShop(carId, shopId)).thenReturn(car);
        when(mapper.mapToDto(car)).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(post(path + "/{shopId}/cars/{carId}", shopId, carId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void delete_WhenCarIsDeleted_ReturnOk() throws Exception {

        // Arrange
        int carId = 1;
        String shopId = "664f5816c7a56c41976302c8";

        // Act & Assert
        mockMvc.perform(delete(path + "/{shopId}/cars/{carId}", shopId, carId))
                .andExpect(status().isOk());

        verify(carsInShopService, times(1)).deleteCarFromShop(carId, shopId);
    }
}
