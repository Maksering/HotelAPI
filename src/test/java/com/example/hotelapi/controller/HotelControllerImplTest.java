package com.example.hotelapi.controller;

import com.example.hotelapi.controller.impl.HotelControllerImpl;
import com.example.hotelapi.dto.CreateHotelDto;
import com.example.hotelapi.dto.HotelDto;
import com.example.hotelapi.dto.HotelSummaryDto;
import com.example.hotelapi.service.HotelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HotelControllerImplTest {

    @Mock
    private HotelService hotelService;

    @InjectMocks
    private HotelControllerImpl hotelController;

    @Test
    void getAllHotels_ShouldReturnOkResponseWithHotelsList() {
        List<HotelSummaryDto> expectedHotels = List.of(
                new HotelSummaryDto(),
                new HotelSummaryDto()
        );
        when(hotelService.getAllHotels()).thenReturn(expectedHotels);


        ResponseEntity<List<HotelSummaryDto>> response = hotelController.getAllHotels();


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedHotels, response.getBody());
        verify(hotelService, times(1)).getAllHotels();
    }

    @Test
    void createHotel_ShouldReturnCreatedHotelSummary() {
        CreateHotelDto inputDto = new CreateHotelDto();
        HotelSummaryDto expectedSummary = new HotelSummaryDto();
        expectedSummary.setId(1L);
        expectedSummary.setName("New Hotel");
        when(hotelService.createHotel(inputDto)).thenReturn(expectedSummary);


        ResponseEntity<HotelSummaryDto> response = hotelController.createHotel(inputDto);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedSummary, response.getBody());
        verify(hotelService, times(1)).createHotel(inputDto);
    }

    @Test
    void getHotelById_WithValidId_ShouldReturnHotelDto() {
        long hotelId = 1L;
        HotelDto expectedHotel = new HotelDto();
        expectedHotel.setId(hotelId);
        when(hotelService.getHotelById(hotelId)).thenReturn(expectedHotel);


        ResponseEntity<HotelDto> response = hotelController.getHotelById(hotelId);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedHotel, response.getBody());
        verify(hotelService, times(1)).getHotelById(hotelId);
    }

    @Test
    void addAmenitiesToHotel_WithValidParams_ShouldReturnNoContent() {
        long hotelId = 1L;
        List<String> amenities = List.of("WiFi", "Gym");


        ResponseEntity<Void> response = hotelController.addAmenitiesToHotel(hotelId, amenities);


        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(hotelService, times(1)).addAmenitiesToHotel(hotelId, amenities);
    }

    @Test
    void searchHotels_WithValidParams_ShouldReturnMatchingHotels() {
        String name = "Test Hotel";
        String brand = "Test Brand";
        String city = "New York";
        String country = "USA";
        List<String> amenities = List.of("WiFi", "Pool");

        List<HotelSummaryDto> expectedHotels = List.of(
                new HotelSummaryDto(),
                new HotelSummaryDto()
        );

        when(hotelService.searchHotels(name, brand, city, country, amenities))
                .thenReturn(expectedHotels);


        ResponseEntity<List<HotelSummaryDto>> response =
                hotelController.searchHotels(name, brand, city, country, amenities);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedHotels, response.getBody());
        verify(hotelService, times(1)).searchHotels(name, brand, city, country, amenities);
    }

    @Test
    void searchHotels_WithNullAndNonNullParams_ShouldCallServiceCorrectly() {
        String name = "Test Hotel";
        String brand = null;
        String city = "New York";
        String country = null;
        List<String> amenities = List.of("WiFi");

        List<HotelSummaryDto> expectedHotels = List.of(new HotelSummaryDto());
        when(hotelService.searchHotels(name, brand, city, country, amenities))
                .thenReturn(expectedHotels);


        ResponseEntity<List<HotelSummaryDto>> response =
                hotelController.searchHotels(name, brand, city, country, amenities);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedHotels, response.getBody());
        verify(hotelService, times(1)).searchHotels(name, brand, city, country, amenities);
    }

    @Test
    void getHistogram_WithValidParam_ShouldReturnHistogramMap() {
        String param = "amenities";
        Map<String, Long> expectedHistogram = new HashMap<>();
        expectedHistogram.put("WiFi", 10L);
        expectedHistogram.put("Pool", 5L);
        expectedHistogram.put("Gym", 3L);

        when(hotelService.getHistogram(param)).thenReturn(expectedHistogram);


        ResponseEntity<Map<String, Long>> response = hotelController.getHistogram(param);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedHistogram, response.getBody());
        verify(hotelService, times(1)).getHistogram(param);
    }

}
