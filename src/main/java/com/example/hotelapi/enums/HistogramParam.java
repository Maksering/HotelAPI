package com.example.hotelapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HistogramParam {
    BRAND("brand"),
    CITY("city"),
    COUNTRY("country"),
    AMENITIES("amenities");

    private final String value;

    public static HistogramParam fromValue(String value) {
        for (HistogramParam param : values()) {
            if (param.getValue().equalsIgnoreCase(value)) {
                return param;
            }
        }
        return null;
    }
}
