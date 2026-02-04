package com.example.hotelapi.controller;

import com.example.hotelapi.dto.CreateHotelDto;
import com.example.hotelapi.dto.HotelDto;
import com.example.hotelapi.dto.HotelSummaryDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Validated
@RequestMapping("/property-view")
public interface HotelController {
    //TODO ДОБАВИТЬ ОПИСАНИЕ

    /**
     * Вывести все отели кратко
     *
     */
    @GetMapping("/hotels")
    ResponseEntity<List<HotelSummaryDto>> getAllHotels();

    /**
     * Создать отель
     *
     */
    @PostMapping("/hotels")
    ResponseEntity<HotelSummaryDto> createHotel(
            @Valid @RequestBody CreateHotelDto createHotelDto
    );

    /**
     * Вывести расширенную информацию по конкретному отелю
     *
     */
    @GetMapping("/hotels/{id}")
    ResponseEntity<HotelDto> getHotelById(
            @PathVariable long id
    );

    /**
     * Добавить удобства для отеля
     *
     */
    @PostMapping("/hotels/{id}/amenities")
    ResponseEntity<Void> addAmenitiesToHotel(
            @PathVariable long id,
            @RequestBody List<String> amenities
    );

    @GetMapping("/search")
    ResponseEntity<List<HotelSummaryDto>> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) List<String> amenitites
    );

    @GetMapping("/histogram/{param}")
    ResponseEntity<Map<String, Long>> getHistogram(
            @PathVariable String param
    );

}
