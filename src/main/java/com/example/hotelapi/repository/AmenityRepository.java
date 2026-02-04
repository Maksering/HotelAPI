package com.example.hotelapi.repository;

import com.example.hotelapi.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity,Long> {
    Amenity findByName(String name);
}
