package com.example.hotelapi.service.impl;

import com.example.hotelapi.dto.CreateHotelDto;
import com.example.hotelapi.dto.HotelDto;
import com.example.hotelapi.dto.HotelSummaryDto;
import com.example.hotelapi.entity.Amenity;
import com.example.hotelapi.entity.Hotel;
import com.example.hotelapi.exception.HotelNotFoundException;
import com.example.hotelapi.mapper.HotelMapper;
import com.example.hotelapi.repository.AmenityRepository;
import com.example.hotelapi.repository.HotelRepository;
import com.example.hotelapi.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;
    private final HotelMapper hotelMapper;

    @Override
    public List<HotelSummaryDto> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(hotelMapper::toHotelSummaryDto)
                .toList();
    }

    @Override
    public HotelSummaryDto createHotel(CreateHotelDto createHotelDto) {
        Hotel hotel = hotelRepository.save(Hotel.builder()
                .name(createHotelDto.getName())
                .description(createHotelDto.getDescription())
                .brand(createHotelDto.getBrand())
                .address(createHotelDto.getAddress())
                .phone(createHotelDto.getContacts().getPhone())
                .email(createHotelDto.getContacts().getEmail())
                .checkInTime(LocalTime.parse(createHotelDto.getArrivalTime().getCheckIn()))
                .checkOutTime(
                        createHotelDto.getArrivalTime().getCheckOut() != null
                                ? LocalTime.parse(createHotelDto.getArrivalTime().getCheckOut())
                                : null
                )
                .build()
        );

        return hotelMapper.toHotelSummaryDto(hotel);
    }

    @Override
    public HotelDto getHotelById(long id) {
        return hotelMapper.toHotelDto(
                hotelRepository.findByIdWithAmenities(id).orElseThrow(() -> new HotelNotFoundException(id))
        );
    }

    @Override
    public void addAmenitiesToHotel(long id, List<String> amenitiesList) {
        if (amenitiesList.isEmpty()) {
            return;
        }
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException(id));

        Set<Amenity> amenities = amenitiesList.stream()
                .map(name -> {
                            Amenity amenity = amenityRepository.findByName(name);
                            if (amenity == null) {
                                amenity = new Amenity();
                                amenity.setName(name);
                                amenity = amenityRepository.save(amenity);
                            }
                            return amenity;
                        }
                )
                .collect(Collectors.toSet());

        hotel.getAmenities().addAll(amenities);
        hotelRepository.save(hotel);
    }

}
