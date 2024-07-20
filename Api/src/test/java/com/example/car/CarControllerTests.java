package com.example.car;

import com.example.car.dto.CarRequestDto;
import com.example.car.dto.CarResponseDto;
import com.example.controllers.car.CarController;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
public class CarControllerTests {

    private final String path = "/api/v1/cars";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private CarServiceMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void get_ReturnsAllCars() throws Exception {
        // Arrange
        List<Car> cars = Arrays.asList(
                new Car(1, "Car1", 4, true),
                new Car(2, "Car2", 4, false),
                new Car(3, "Car3", 4, false));

        List<CarResponseDto> dtoList = Arrays.asList(
                new CarResponseDto(1, "Car1", 4, true),
                new CarResponseDto(2, "Car2", 4, false),
                new CarResponseDto(3, "Car3", 4, false));

        when(carService.getAll()).thenReturn(cars);
        when(mapper.mapToDtoList(cars)).thenReturn(dtoList);

        // Act
        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Car1")))
                .andExpect(jsonPath("$[1].name", is("Car2")))
                .andExpect(jsonPath("$[2].name", is("Car3")));
    }

    @Test
    public void get_IfRecordExistsById_ReturnCar() throws Exception {

        // Arrange
        int idToSearch = 1;

        Car car = new Car(idToSearch, "Car1", 4, true);

        CarResponseDto dto = new CarResponseDto(idToSearch, "Car1", 4, true);

        when(carService.getById(idToSearch)).thenReturn(car);
        when(mapper.mapToDto(car)).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(get(path + "/{id}", idToSearch)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dto)));
    }

    @Test
    void get_IfRecordIsAbsentById_ReturnNotFound() throws Exception {
        // Arrange
        int nonExistingId = 999;
        when(carService.getById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        // Act & Assert
        mockMvc.perform(get(path + "/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
    }


    @Test
    void post_IfInputIsValid_ReturnCreatedCar() throws Exception {
        // Arrange
        CarRequestDto createDto = new CarRequestDto("Car1", 4, true);
        Car model = new Car(1, "Car1", 4, true);
        CarResponseDto responseDto = new CarResponseDto(1, "Car1", 4, true);

        when(mapper.mapToModel(createDto)).thenReturn(model);
        when(carService.create(model)).thenReturn(model);
        when(mapper.mapToDto(model)).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void post_IfNameIsTooLong_ReturnBadRequest() throws Exception {
        // Arrange
        String longName = "a".repeat(257);
        CarRequestDto createDto = new CarRequestDto(longName, 4, true);

        // Act & Assert
        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("Should not be greater than 256 chars")));
    }

    @Test
    void post_IfNameIsBlank_ReturnBadRequest() throws Exception {
        // Arrange
        String emptyName = " "; // or null
        CarRequestDto createDto = new CarRequestDto(emptyName, 4, true);

        // Act & Assert
        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("Name must be specified")));
    }

    @Test
    void post_IfDoorsCountIsInvalidMin_ReturnBadRequest() throws Exception {
        // Arrange
        int doorsCount = -4;
        CarRequestDto createDto = new CarRequestDto("Car1", doorsCount, true);

        // Act & Assert
        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.doorsCount", is("Doors count must be between 1 and 10")));
    }

    @Test
    void put_IfDoorsCountIsInvalidMax_ReturnBadRequest() throws Exception {
        // Arrange
        int idToUpdate = 1;
        int doorsCount = 55;
        CarRequestDto updateDto = new CarRequestDto("Car1", doorsCount, true);

        // Act & Assert
        mockMvc.perform(put(path + "/{id}", idToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.doorsCount", is("Doors count must be between 1 and 10")));
    }

    @Test
    void put_IfNameIsNull_ReturnBadRequest() throws Exception {
        // Arrange
        int idToUpdate = 1;
        CarRequestDto updateDto = new CarRequestDto(null, 4, true);

        // Act & Assert
        mockMvc.perform(put(path + "/{id}", idToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("Name must be specified")));
    }

    @Test
    void delete_WhenCarIsDeleted_ReturnOk() throws Exception {
        // Arrange
        int idToDelete = 1;

        // Act & Assert
        mockMvc.perform(delete(path + "/{id}", idToDelete))
                .andExpect(status().isOk());

        verify(carService, times(1)).deleteById(idToDelete);
    }
}
