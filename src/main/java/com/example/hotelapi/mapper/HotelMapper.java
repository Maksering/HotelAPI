package com.example.hotelapi.mapper;

import com.example.hotelapi.dto.HotelSummaryDto;
import com.example.hotelapi.entity.Address;
import com.example.hotelapi.entity.Hotel;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HotelMapper {
    public HotelSummaryDto toSummaryDto(Hotel h) {
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
}
