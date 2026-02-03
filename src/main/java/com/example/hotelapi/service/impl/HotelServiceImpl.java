package com.example.hotelapi.service.impl;

import com.example.hotelapi.dto.HotelSummaryDto;
import com.example.hotelapi.mapper.HotelMapper;
import com.example.hotelapi.repository.HotelRepository;
import com.example.hotelapi.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    public List<HotelSummaryDto> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(hotelMapper::toSummaryDto)
                .toList();
    }
}
