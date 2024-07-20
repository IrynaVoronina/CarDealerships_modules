package com.example.controllers.cars_in_shop;

import com.example.car.Car;
import com.example.car.CarServiceMapper;
import com.example.car.dto.CarResponseDto;
import com.example.cars_in_shop.CarsInShopService;
import com.example.cars_in_shop.inventory_event.InventoryEvent;
import com.example.rabbitMQ.RabbitMQPublisher;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/shops", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarsInShopController {

    CarsInShopService carsInShopService;
    CarServiceMapper mapper;

    RabbitMQPublisher rabbitMQPublisher;


    @GetMapping("/{shopId}/cars")
    public ResponseEntity<List<CarResponseDto>> getAllCarsByShop(@PathVariable String shopId) {
        return ResponseEntity.ok().body(mapper.mapToDtoList(carsInShopService.getCarsByShop(shopId)));
    }

    @PostMapping("/{shopId}/cars/{carId}")
    public ResponseEntity<CarResponseDto> addCarToShop(@PathVariable String shopId,
                                                       @PathVariable int carId) {
        Car car = carsInShopService.addCarToShop(carId, shopId);
        InventoryEvent inventoryEvent = InventoryEvent.builder()
                .date(LocalDate.now())
                .carId(carId)
                .shoppId(shopId)
                .build();
        rabbitMQPublisher.publishInventoryEvent(inventoryEvent);
                return ResponseEntity.ok().body(mapper.mapToDto(car));
    }

    @DeleteMapping("/{shopId}/cars/{carId}")
    public ResponseEntity<Void> deleteCarFromShop(@PathVariable String shopId,
                                                  @PathVariable int carId) {
        carsInShopService.deleteCarFromShop(carId, shopId);
        return ResponseEntity.ok().build();
    }
}
