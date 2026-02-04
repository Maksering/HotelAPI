package com.example.hotelapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HistogramElementDto {
    private String name;
    private long count;
}
