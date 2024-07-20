package com.example.cars_in_shop;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryEventJpaRepository extends JpaRepository<InventoryEventDao, Integer> {
}