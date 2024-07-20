package com.example.controllers.shop;

import com.example.shop.Shop;
import com.example.shop.ShopService;
import com.example.shop.ShopServiceMapper;
import com.example.shop.dto.ShopRequestDto;
import com.example.shop.dto.ShopResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/v1/shops", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShopController {

    ShopService shopService;
    ShopServiceMapper mapper;

    @GetMapping()
    public ResponseEntity<List<ShopResponseDto>> getAll() {
        return ResponseEntity.ok().body(mapper.mapToDtoList(shopService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopResponseDto> getById(@PathVariable String id) {
        return ResponseEntity.ok().body(mapper.mapToDto(shopService.getById(id)));
    }

    @PostMapping()
    public ResponseEntity<ShopResponseDto> create(@RequestBody @Valid ShopRequestDto createDto) {
        Shop shop = mapper.mapToModel(createDto);
        Shop createdShop = shopService.create(shop);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapToDto(createdShop));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShopResponseDto> update(@PathVariable String id,
                                                  @RequestBody @Valid ShopRequestDto updateDto) {
        Shop shop = mapper.mapToModel(updateDto);
        shop.setId(id);
        Shop updatedChemicalElement = shopService.update(shop);
        return ResponseEntity.ok().body(mapper.mapToDto(updatedChemicalElement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        shopService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
