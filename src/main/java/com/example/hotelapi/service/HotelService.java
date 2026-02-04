package com.example.hotelapi.service;

import com.example.hotelapi.dto.CreateHotelDto;
import com.example.hotelapi.dto.HotelDto;
import com.example.hotelapi.dto.HotelSummaryDto;

import java.util.List;

public interface HotelService {

    List<HotelSummaryDto> getAllHotels();

    HotelSummaryDto createHotel(CreateHotelDto createHotelDto);

    HotelDto getHotelById(long id);

    void addAmenitiesToHotel(long id, List<String> amenities);

}
