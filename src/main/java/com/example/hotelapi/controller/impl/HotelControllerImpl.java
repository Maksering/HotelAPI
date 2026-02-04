package com.example.hotelapi.controller.impl;

import com.example.hotelapi.controller.HotelController;
import com.example.hotelapi.dto.CreateHotelDto;
import com.example.hotelapi.dto.HotelDto;
import com.example.hotelapi.dto.HotelSummaryDto;
import com.example.hotelapi.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HotelControllerImpl implements HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelControllerImpl(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Override
    public ResponseEntity<List<HotelSummaryDto>> getAllHotels() {
        List<HotelSummaryDto> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @Override
    public ResponseEntity<HotelSummaryDto> createHotel(CreateHotelDto createHotelDto) {
        HotelSummaryDto hotelSummary = hotelService.createHotel(createHotelDto);
        return ResponseEntity.ok(hotelSummary);
    }

    @Override
    public ResponseEntity<HotelDto> getHotelById(long id) {
        HotelDto hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotel);
    }

    @Override
    public ResponseEntity<Void> addAmenitiesToHotel(long id, List<String> amenities) {
        hotelService.addAmenitiesToHotel(id,amenities);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<HotelSummaryDto>> searchHotels(
            String name, String brand, String city, String country, List<String> amenities
    ) {
        List<HotelSummaryDto> hotels = hotelService.searchHotels(name,brand,city,country,amenities);
        return ResponseEntity.ok(hotels);
    }

}
