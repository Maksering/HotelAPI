package com.example.hotelapi.mapper;

import com.example.hotelapi.dto.HistogramElementDto;
import com.example.hotelapi.dto.HotelDto;
import com.example.hotelapi.dto.HotelSummaryDto;
import com.example.hotelapi.entity.Address;
import com.example.hotelapi.entity.Amenity;
import com.example.hotelapi.entity.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class HotelMapperTest {

    @InjectMocks
    private HotelMapper hotelMapper;

    private Hotel hotel;
    private Address address;
    private Set<Amenity> amenities;

    @BeforeEach
    void setUp(){
        hotel = new Hotel();
        address = new Address();
        amenities = new HashSet<>();
    }

    @Test
    void toHotelSummaryDto_WithCorrectData_ShouldReturnCorrectDto() {
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setDescription("Test Description");
        hotel.setPhone("123-456-7890");
        address.setHouseNumber("123");
        address.setStreet("Main St");
        address.setCity("New York");
        address.setCountry("USA");
        address.setPostCode("10001");
        hotel.setAddress(address);


        HotelSummaryDto result = hotelMapper.toHotelSummaryDto(hotel);


        assertEquals(1L, result.getId());
        assertEquals("Test Hotel", result.getName());
        assertEquals("Test Description", result.getDescription());
        assertEquals("123 Main St, New York, USA, 10001", result.getAddress());
        assertEquals("123-456-7890", result.getPhone());
    }

    @Test
    void toHotelDto_WithCorrectData_ShouldReturnCorrectDto() {
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setDescription("Test Description");
        hotel.setBrand("Test Brand");
        hotel.setPhone("123-456-7890");
        hotel.setEmail("test@hotel.com");
        hotel.setCheckInTime(LocalTime.of(14, 0));
        hotel.setCheckOutTime(LocalTime.of(12, 0));
        Amenity amenity1 = new Amenity();
        amenity1.setName("WiFi");
        Amenity amenity2 = new Amenity();
        amenity2.setName("Pool");
        amenities.add(amenity1);
        amenities.add(amenity2);
        hotel.setAmenities(amenities);
        hotel.setAddress(address);


        HotelDto result = hotelMapper.toHotelDto(hotel);


        assertEquals(1L, result.getId());
        assertEquals("Test Hotel", result.getName());
        assertEquals("Test Description", result.getDescription());
        assertEquals("Test Brand", result.getBrand());
        assertEquals(address, result.getAddress());

        assertNotNull(result.getContacts());
        assertEquals("123-456-7890", result.getContacts().getPhone());
        assertEquals("test@hotel.com", result.getContacts().getEmail());

        assertNotNull(result.getArrivalTime());
        assertEquals("14:00", result.getArrivalTime().getCheckIn());
        assertEquals("12:00", result.getArrivalTime().getCheckOut());

        assertNotNull(result.getAmenities());
        assertEquals(2, result.getAmenities().size());
        assertTrue(result.getAmenities().contains("WiFi"));
        assertTrue(result.getAmenities().contains("Pool"));
    }

    @Test
    void toHotelDto_WithNullCheckOutTime_ShouldHandleCorrectly() {
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setCheckInTime(LocalTime.of(14, 0));
        hotel.setCheckOutTime(null);
        hotel.setAmenities(new HashSet<>());


        HotelDto result = hotelMapper.toHotelDto(hotel);


        assertNotNull(result.getArrivalTime());
        assertEquals("14:00", result.getArrivalTime().getCheckIn());
        assertNull(result.getArrivalTime().getCheckOut());
    }

    @Test
    void toHistogramMap_WithElements_ShouldReturnCorrectMap() {
        List<HistogramElementDto> elements = new ArrayList<>();

        HistogramElementDto element1 = new HistogramElementDto();
        element1.setName("Category A");
        element1.setCount(5L);

        HistogramElementDto element2 = new HistogramElementDto();
        element2.setName("Category B");
        element2.setCount(10L);

        elements.add(element1);
        elements.add(element2);


        Map<String, Long> result = hotelMapper.toHistogramMap(elements);


        assertEquals(2, result.size());
        assertEquals(Long.valueOf(5), result.get("Category A"));
        assertEquals(Long.valueOf(10), result.get("Category B"));
        assertInstanceOf(LinkedHashMap.class, result);
    }

}
