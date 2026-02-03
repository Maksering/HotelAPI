package com.example.hotelapi.dto;

import com.example.hotelapi.entity.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateHotelDto {
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    @NotBlank(message = "Brand is required")
    private String brand;
    @NotNull(message = "Address is required")
    @Valid
    private Address address;
    @NotNull(message = "Contacts is required")
    @Valid
    private HotelContacts contacts;
    @NotNull(message = "ArrivalTime is required")
    @Valid
    private HotelArrivalTime arrivalTime;
}
