package com.example.hotelapi.dto;

import com.example.hotelapi.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private Address address;
    private HotelContacts contacts;
    private HotelArrivalTime arrivalTime;
    private List<String> amenities;
}
