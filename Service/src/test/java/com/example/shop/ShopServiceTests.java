package com.example.shop;

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
public class ShopServiceTests {

    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopServiceImpl shopService;


    @Test
    void getAll_ReturnAllShops() {
        // Arrange
        List<Shop> expectedShops = Arrays.asList(
                new Shop("1", "Shop1", 122),
                new Shop("2", "Shop2", 109),
                new Shop("3", "Shop3", 876)
        );
        when(shopRepository.getAll()).thenReturn(expectedShops);

        // Act
        List<Shop> actualShops = shopService.getAll();

        // Assert
        assertEquals(expectedShops, actualShops);
    }


    @Test
    void getById_IfExists_ReturnShop() {
        // Arrange
        String id = "100";
        Shop expectedShop = new Shop(id, "Shop", 300);

        when(shopRepository.getById(id)).thenReturn(expectedShop);

        // Act
        Shop actualShop = shopService.getById(id);

        // Assert
        assertEquals(expectedShop, actualShop);
    }


    @Test
    void getById_IfAbsent_ThrowsResourceNotFoundException() {
        // Arrange
        String nonExistingId = "999";
        when(shopRepository.getById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            shopService.getById(nonExistingId);
        });
    }

    @Test
    void create_ReturnsCreatedShop() {
        // Arrange
        Shop shop = new Shop("1", "Shop1", 123);

        when(shopRepository.create(any(Shop.class))).thenReturn(shop);

        // Act
        Shop createdShop = shopService.create(shop);

        // Assert
        assertEquals(shop, createdShop);
    }


    @Test
    void update_ReturnsUpdatedShop() {
        // Arrange
        String id = "1";
        Shop shop = new Shop(id, "Shop1", 123);

        when(shopRepository.update(any(Shop.class))).thenReturn(shop);

        // Act
        Shop update = shopService.update(shop);

        // Assert
        assertEquals(shop, update);
    }

    @Test
    void deleteById_IfElementExists_DeleteShop() {
        // Arrange
        String id = "1";

        // Act
        shopService.deleteById(id);

        // Assert
        verify(shopRepository, times(1)).getById(id);
        verify(shopRepository, times(1)).deleteById(id);
    }
}
