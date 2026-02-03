package com.example.hotelapi.repository;

import com.example.hotelapi.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h LEFT JOIN FETCH h.amenities WHERE h.id = :id")
    Optional<Hotel> findByIdWithAmenities(Long id);
}
