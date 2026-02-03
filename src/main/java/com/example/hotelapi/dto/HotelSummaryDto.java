package com.example.hotelapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelSummaryDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
