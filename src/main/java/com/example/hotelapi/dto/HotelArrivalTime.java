package com.example.hotelapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class HotelArrivalTime {
    @NotBlank(message = "CheckInTime is required")
    @Pattern(regexp = "^[0-2]\\d:[0-5]\\d$", message = "Некорректный формат времени (HH:mm)")
    private String checkIn;
    @Pattern(regexp = "^[0-2]\\d:[0-5]\\d$", message = "Некорректный формат времени (HH:mm)")
    private String checkOut;
}
