package com.example.hotelapi.service;

import com.example.hotelapi.dto.HotelDto;
import com.example.hotelapi.dto.HotelSummaryDto;

import java.util.List;

public interface HotelService {

    List<HotelSummaryDto> getAllHotels();

    HotelDto getHotelById(long id);

}
