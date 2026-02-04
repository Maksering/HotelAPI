package com.example.hotelapi.service;

import com.example.hotelapi.dto.CreateHotelDto;
import com.example.hotelapi.dto.HotelDto;
import com.example.hotelapi.dto.HotelSummaryDto;

import java.util.List;
import java.util.Map;

public interface HotelService {

    List<HotelSummaryDto> getAllHotels();

    HotelSummaryDto createHotel(CreateHotelDto createHotelDto);

    HotelDto getHotelById(long id);

    void addAmenitiesToHotel(long id, List<String> amenities);

    List<HotelSummaryDto> searchHotels(String name, String brand, String city, String country, List<String> amenities);

    Map<String, Long> getHistogram(String param);
}
