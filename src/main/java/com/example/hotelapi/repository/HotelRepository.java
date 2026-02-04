package com.example.hotelapi.repository;

import com.example.hotelapi.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h LEFT JOIN FETCH h.amenities WHERE h.id = :id")
    Optional<Hotel> findByIdWithAmenities(Long id);

    @Query("SELECT DISTINCT h FROM Hotel h JOIN h.amenities a WHERE LOWER(a.name) IN :amenities")
    List<Hotel> findByAmenities(List<String> amenities);

    @Query("SELECT h FROM Hotel h WHERE " +
            "(COALESCE(:name, '') = '' OR LOWER(h.name) LIKE LOWER(:name)) AND " +
            "(COALESCE(:brand, '') = '' OR LOWER(h.brand) LIKE LOWER(:brand)) AND " +
            "(COALESCE(:city, '') = '' OR LOWER(h.address.city) LIKE LOWER(:city)) AND " +
            "(COALESCE(:country, '') = '' OR LOWER(h.address.country) LIKE LOWER(:country))")
    List<Hotel> searchHotels(String name, String brand, String city, String country);
}
