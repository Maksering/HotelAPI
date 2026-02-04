package com.example.hotelapi.repository;

import com.example.hotelapi.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AmenityRepository extends JpaRepository<Amenity,Long> {
    @Query("SELECT a FROM Amenity a WHERE LOWER(a.name) = LOWER(:name)")
    Amenity findByName(String name);
}
