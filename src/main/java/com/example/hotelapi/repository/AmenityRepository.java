package com.example.hotelapi.repository;

import com.example.hotelapi.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AmenityRepository extends JpaRepository<Amenity,Long> {
    Amenity findByNameIgnoreCase(String name);

    List<Amenity> findAllByNameInIgnoreCase(Collection<String> names);
    
}
