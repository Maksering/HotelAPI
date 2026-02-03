package com.example.hotelapi.controller;

import com.example.hotelapi.dto.HotelSummaryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/property-view")
public interface HotelController {

    /**
     * Потом добавить документацию
     *
     */
    @GetMapping("/hotels")
    ResponseEntity<List<HotelSummaryDto>> getAllHotels();

}
