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

import java.util.List;

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
     * Вывести расширенную информацию по конкретному отелю
     *
     */
    @GetMapping("/hotels/{id}")
    ResponseEntity<HotelDto> getHotelById(
            @PathVariable long id
    );

    @PostMapping("/hotels")
    ResponseEntity<HotelSummaryDto> createHotel(
            @Valid @RequestBody CreateHotelDto createHotelDto
    );

}
