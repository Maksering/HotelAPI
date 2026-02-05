package com.example.hotelapi.controller;

import com.example.hotelapi.dto.CreateHotelDto;
import com.example.hotelapi.dto.HotelDto;
import com.example.hotelapi.dto.HotelSummaryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Validated
@RequestMapping("/property-view")
public interface HotelController {

    @Operation(
            summary = "Get all hotels",
            description = "Returns a list of all hotels with their summary"
    )
    @ApiResponse(
            responseCode = "200", description = "Successfully retrieved list of hotels",
            content = @Content(schema = @Schema(implementation = HotelSummaryDto.class))
    )
    @GetMapping("/hotels")
    ResponseEntity<List<HotelSummaryDto>> getAllHotels();

    @Operation(
            summary = "Get hotel by ID",
            description = "Get detailed hotel information by itd ID"
    )
    @ApiResponse(
            responseCode = "200", description = "Hotel found and returned successfully",
            content = @Content(schema = @Schema(implementation = HotelSummaryDto.class))
    )
    @ApiResponse(
            responseCode = "404", description = "Hotel not found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Parameter(name = "id", description = "ID of the hotel to update", example = "1")
    @GetMapping("/hotels/{id}")
    ResponseEntity<HotelDto> getHotelById(
            @PathVariable long id
    );

    @Operation(
            summary = "Search hotels",
            description = "Searches hotels by optional parameters: name, brand, city, country, and amenities."
    )
    @Parameter(name = "name", description = "Filter by hotel name", example = "Grand Hotel", required = false)
    @Parameter(name = "brand", description = "Filter by hotel brand", example = "Hilton", required = false)
    @Parameter(name = "city", description = "Filter by city", example = "Minsk", required = false)
    @Parameter(name = "country", description = "Filter by country", example = "Belarus", required = false)
    @Parameter(name = "amenities", description = "Filter by amenities (can be multiple)", example = "Free WiFi", required = false)
    @ApiResponse(responseCode = "200", description = "Successfully retrieved matching hotels",
            content = @Content(schema = @Schema(implementation = HotelSummaryDto.class)))
    @GetMapping("/search")
    ResponseEntity<List<HotelSummaryDto>> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) List<String> amenities
    );

    @Operation(
            summary = "Create a new hotel",
            description = "Create a new hotel with details"
    )
    @ApiResponse(
            responseCode = "200", description = "Successfully created hotel",
            content = @Content(schema = @Schema(implementation = HotelSummaryDto.class))
    )
    @ApiResponse(responseCode = "400", description = "Invalid input data",
            content = @Content(schema = @Schema(implementation = String.class))
    )


    @PostMapping("/hotels")
    ResponseEntity<HotelSummaryDto> createHotel(
            @Valid @RequestBody CreateHotelDto createHotelDto
    );

    @Operation(
            summary = "Add amenities to a hotel",
            description = "Add a list of amenities to a hotel by its id"
    )
    @ApiResponse(
            responseCode = "204", description = "Amenities added successfully",
            content = @Content(schema = @Schema(implementation = HotelSummaryDto.class))
    )
    @ApiResponse(
            responseCode = "404", description = "Hotel not found",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @Parameter(name = "id", description = "ID of the hotel to update", example = "1")
    @PostMapping("/hotels/{id}/amenities")
    ResponseEntity<Void> addAmenitiesToHotel(
            @PathVariable long id,
            @RequestBody List<String> amenities
    );

    @Operation(
            summary = "Get hotels histogram",
            description = "Returns a map of counts grouped by the specified parameter: brand, city, country, amenities."
    )
    @Parameter(name = "param", description = "Parameter to group by", example = "city", required = true)
    @ApiResponse(responseCode = "200", description = "Histogram data returned successfully",
            content = @Content(schema = @Schema(implementation = Map.class)))
    @ApiResponse(responseCode = "400", description = "Invalid parameter",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @GetMapping("/histogram/{param}")
    ResponseEntity<Map<String, Long>> getHistogram(
            @PathVariable String param
    );

}
