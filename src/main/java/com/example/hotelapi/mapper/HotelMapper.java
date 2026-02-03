package com.example.hotelapi.mapper;

import com.example.hotelapi.dto.HotelArrivalTime;
import com.example.hotelapi.dto.HotelContacts;
import com.example.hotelapi.dto.HotelDto;
import com.example.hotelapi.dto.HotelSummaryDto;
import com.example.hotelapi.entity.Address;
import com.example.hotelapi.entity.Amenity;
import com.example.hotelapi.entity.Hotel;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class HotelMapper {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public HotelSummaryDto toHotelSummaryDto(Hotel h) {
        String addrStr = "";
        if (h.getAddress() != null) {
            Address addr = h.getAddress();
            addrStr = String.format("%s %s, %s, %s, %s",
                    Optional.ofNullable(addr.getHouseNumber()).orElse(""),
                    Optional.ofNullable(addr.getStreet()).orElse(""),
                    Optional.ofNullable(addr.getCity()).orElse(""),
                    Optional.ofNullable(addr.getCountry()).orElse(""),
                    Optional.ofNullable(addr.getPostCode()).orElse("")
            ).trim();
        }

        return HotelSummaryDto.builder()
                .id(h.getId())
                .name(h.getName())
                .description(h.getDescription())
                .address(addrStr)
                .phone(h.getPhone())
                .build();
    }

    public HotelDto toHotelDto(Hotel hotel) {
        HotelContacts hotelContacts = HotelContacts.builder()
                .phone(hotel.getPhone())
                .email(hotel.getEmail())
                .build();

        HotelArrivalTime hotelArrivalTime = HotelArrivalTime.builder()
                .checkIn(formatter.format(hotel.getCheckInTime()))
                .checkOut(formatter.format(hotel.getCheckOutTime()))
                .build();

        List<String> amenities = hotel.getAmenities().stream()
                .map(Amenity::getName)
                .toList();

        return HotelDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .brand(hotel.getBrand())
                .address(hotel.getAddress())
                .contacts(hotelContacts)
                .arrivalTime(hotelArrivalTime)
                .amenities(amenities)
                .build();
    }
}
