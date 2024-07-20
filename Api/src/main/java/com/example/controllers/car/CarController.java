package com.example.controllers.car;


import com.example.car.Car;
import com.example.car.CarService;
import com.example.car.CarServiceMapper;
import com.example.car.dto.CarRequestDto;
import com.example.car.dto.CarResponseDto;
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
@RequestMapping(value ="/api/v1/cars", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {

    CarService carService;
    CarServiceMapper mapper;

    @GetMapping()
    public ResponseEntity<List<CarResponseDto>> getAll() {
        return ResponseEntity.ok().body(mapper.mapToDtoList(carService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDto> getById(@PathVariable int id) {
        return ResponseEntity.ok().body(mapper.mapToDto(carService.getById(id)));
    }

    @PostMapping()
    public ResponseEntity<CarResponseDto> create(@RequestBody @Valid CarRequestDto createDto) {
        Car carToCreate = mapper.mapToModel(createDto);
        Car createdCar = carService.create(carToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapToDto(createdCar));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDto> update(@PathVariable int id,
                                                 @RequestBody @Valid CarRequestDto updateDto) {
        Car carToUpdate = mapper.mapToModel(updateDto);
        carToUpdate.setId(id);
        Car updatedCar = carService.update(carToUpdate);
        return ResponseEntity.ok().body(mapper.mapToDto(updatedCar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        carService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
