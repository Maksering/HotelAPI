package com.example.hotelapi.repository;

import com.example.hotelapi.dto.HistogramElementDto;
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

    @Query("SELECT h FROM Hotel h JOIN h.amenities a WHERE LOWER(a.name) IN :amenitiesLower GROUP BY h.id HAVING COUNT(a.id) = :amenitiesSize")
    List<Hotel> findByAmenitiesAllMatch(List<String> amenitiesLower, int amenitiesSize);

    @Query("SELECT h FROM Hotel h WHERE " +
            "(COALESCE(:name, '') = '' OR LOWER(h.name) LIKE LOWER(:name)) AND " +
            "(COALESCE(:brand, '') = '' OR LOWER(h.brand) LIKE LOWER(:brand)) AND " +
            "(COALESCE(:city, '') = '' OR LOWER(h.address.city) LIKE LOWER(:city)) AND " +
            "(COALESCE(:country, '') = '' OR LOWER(h.address.country) LIKE LOWER(:country))")
    List<Hotel> searchHotels(String name, String brand, String city, String country);

    @Query("SELECT new com.example.hotelapi.dto.HistogramElementDto(h.brand, COUNT(h.id)) FROM Hotel h GROUP BY h.brand")
    List<HistogramElementDto> countByBrand();
    @Query("SELECT new com.example.hotelapi.dto.HistogramElementDto(h.address.city, COUNT(h.id)) FROM Hotel h GROUP BY h.address.city")
    List<HistogramElementDto> countByCity();
    @Query("SELECT new com.example.hotelapi.dto.HistogramElementDto(h.address.country, COUNT(h.id)) FROM Hotel h GROUP BY h.address.country")
    List<HistogramElementDto> countByCountry();
    @Query("SELECT new com.example.hotelapi.dto.HistogramElementDto(LOWER(a.name), COUNT(h.id)) FROM Hotel h JOIN h.amenities a GROUP BY LOWER(a.name)")
    List<HistogramElementDto> countByAmenity();

}
