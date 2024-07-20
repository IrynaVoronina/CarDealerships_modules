package com.example.car;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarJpaRepository extends JpaRepository<CarDao, Integer> {
}
