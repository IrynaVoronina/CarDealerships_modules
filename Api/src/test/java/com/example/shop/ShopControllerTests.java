package com.example.shop;


import com.example.controllers.shop.ShopController;
import com.example.shop.dto.ShopRequestDto;
import com.example.shop.dto.ShopResponseDto;
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

@WebMvcTest(ShopController.class)
public class ShopControllerTests {

    private final String path = "/api/v1/shops";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    @MockBean
    private ShopServiceMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void get_ReturnsAllShops() throws Exception {
        // Arrange
        List<Shop> shops = Arrays.asList(
                new Shop("1", "Shop1", 123),
                new Shop("2", "Shop2", 234),
                new Shop("3", "Shop3", 345));

        List<ShopResponseDto> dtoList = Arrays.asList(
                new ShopResponseDto("1", "Shop1", 123),
                new ShopResponseDto("2", "Shop2", 234),
                new ShopResponseDto("3", "Shop3", 345));

        when(shopService.getAll()).thenReturn(shops);
        when(mapper.mapToDtoList(shops)).thenReturn(dtoList);

        // Act
        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Shop1")))
                .andExpect(jsonPath("$[1].name", is("Shop2")))
                .andExpect(jsonPath("$[2].name", is("Shop3")));
    }

    @Test
    public void get_IfRecordExistsById_ReturnShop() throws Exception {

        // Arrange
        String idToSearch = "1";

        Shop shop = new Shop(idToSearch, "Shop1", 123);
        ShopResponseDto dto = new ShopResponseDto(idToSearch, "Shop1", 123);

        when(shopService.getById(idToSearch)).thenReturn(shop);
        when(mapper.mapToDto(shop)).thenReturn(dto);

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
        String nonExistingId = "999";
        when(shopService.getById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        // Act & Assert
        mockMvc.perform(get(path + "/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
    }


    @Test
    void post_IfInputIsValid_ReturnCreatedShop() throws Exception {
        // Arrange
        ShopRequestDto createDto = new ShopRequestDto("Shop1", 123);
        Shop model = new Shop("1", "Shop1", 123);
        ShopResponseDto responseDto = new ShopResponseDto("1", "Shop1", 123);

        when(mapper.mapToModel(createDto)).thenReturn(model);
        when(shopService.create(model)).thenReturn(model);
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
        String longName = "s".repeat(257);
        ShopRequestDto createDto = new ShopRequestDto(longName, 123);

        // Act & Assert
        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("Name should not be greater than 256 chars")));
    }

    @Test
    void post_IfNameIsBlank_ReturnBadRequest() throws Exception {
        // Arrange
        String blankName = " ";
        ShopRequestDto createDto = new ShopRequestDto(blankName, 123);

        // Act & Assert
        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("Name must be specified")));
    }

    @Test
    void put_IfNameIsNull_ReturnBadRequest() throws Exception {
        // Arrange
        String idToUpdate = "1";
        ShopRequestDto updateDto = new ShopRequestDto(null, 123);

        // Act & Assert
        mockMvc.perform(put(path + "/{id}", idToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("Name must be specified")));
    }

    @Test
    void put_IfPlacesAmountIsInvalidMin_ReturnBadRequest() throws Exception {
        // Arrange
        int placesAmount = -1;
        ShopRequestDto updateDto = new ShopRequestDto("Shop1", placesAmount);

        // Act & Assert
        mockMvc.perform(put(path + "/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.placesAmount", is("Amount of places must be between 1 and 1000")));
    }

    @Test
    void put_IfPlacesAmountIsInvalidMax_ReturnBadRequest() throws Exception {
        // Arrange
        int placesAmount = 1001;
        ShopRequestDto updateDto = new ShopRequestDto("Shop1", placesAmount);

        // Act & Assert
        mockMvc.perform(put(path + "/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.placesAmount", is("Amount of places must be between 1 and 1000")));
    }

    @Test
    void delete_WhenShopIsDeleted_ReturnOk() throws Exception {
        // Arrange
        String idToDelete = "1";

        // Act & Assert
        mockMvc.perform(delete(path + "/{id}", idToDelete))
                .andExpect(status().isOk());

        verify(shopService, times(1)).deleteById(idToDelete);
    }
}