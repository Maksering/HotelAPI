package com.example.hotelapi.controller;

import com.example.hotelapi.dto.HotelDto;
import com.example.hotelapi.dto.HotelSummaryDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

}
