package com.example.hotelapi.service;

import com.example.hotelapi.dto.CreateHotelDto;
import com.example.hotelapi.dto.HistogramElementDto;
import com.example.hotelapi.dto.HotelArrivalTime;
import com.example.hotelapi.dto.HotelContacts;
import com.example.hotelapi.dto.HotelDto;
import com.example.hotelapi.dto.HotelSummaryDto;
import com.example.hotelapi.entity.Address;
import com.example.hotelapi.entity.Amenity;
import com.example.hotelapi.entity.Hotel;
import com.example.hotelapi.exception.HotelNotFoundException;
import com.example.hotelapi.exception.InvalidHistogramParamException;
import com.example.hotelapi.mapper.HotelMapper;
import com.example.hotelapi.repository.AmenityRepository;
import com.example.hotelapi.repository.HotelRepository;
import com.example.hotelapi.service.impl.HotelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private AmenityRepository amenityRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Captor
    private ArgumentCaptor<Hotel> hotelCaptor;

    @Captor
    private ArgumentCaptor<Amenity> amenityCaptor;

    private CreateHotelDto createHotelDto;
    private HotelContacts hotelContacts;
    private HotelArrivalTime hotelArrivalTime;
    private Address address;
    private Hotel hotel;
    private HotelSummaryDto hotelSummaryDto;
    private HotelDto hotelDto;

    @BeforeEach
    void setUp() {
        address = new Address();
        address.setHouseNumber("123");
        address.setStreet("Main St");
        address.setCity("New York");
        address.setCountry("USA");
        address.setPostCode("10001");

        hotelContacts = new HotelContacts();
        hotelContacts.setPhone("123-456-7890");
        hotelContacts.setEmail("test@hotel.com");

        hotelArrivalTime = new HotelArrivalTime();
        hotelArrivalTime.setCheckIn("14:00");
        hotelArrivalTime.setCheckOut("12:00");

        createHotelDto = new CreateHotelDto();
        createHotelDto.setName("Test Hotel");
        createHotelDto.setDescription("Test Description");
        createHotelDto.setBrand("Test Brand");
        createHotelDto.setAddress(address);
        createHotelDto.setContacts(hotelContacts);
        createHotelDto.setArrivalTime(hotelArrivalTime);

        hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setDescription("Test Description");
        hotel.setBrand("Test Brand");
        hotel.setAddress(address);
        hotel.setPhone("123-456-7890");
        hotel.setEmail("test@hotel.com");
        hotel.setCheckInTime(LocalTime.of(14, 0));
        hotel.setCheckOutTime(LocalTime.of(12, 0));
        hotel.setAmenities(new HashSet<>());

        hotelSummaryDto = new HotelSummaryDto();
        hotelSummaryDto.setId(1L);
        hotelSummaryDto.setName("Test Hotel");

        hotelDto = new HotelDto();
        hotelDto.setId(1L);
        hotelDto.setName("Test Hotel");
    }

    @Test
    void getAllHotels_ShouldReturnMappedHotelsList() {
        List<Hotel> hotels = List.of(hotel, new Hotel());

        when(hotelRepository.findAll()).thenReturn(hotels);
        when(hotelMapper.toHotelSummaryDto(any(Hotel.class))).thenReturn(hotelSummaryDto);


        List<HotelSummaryDto> result = hotelService.getAllHotels();


        assertEquals(2, result.size());
        verify(hotelRepository, times(1)).findAll();
        verify(hotelMapper, times(2)).toHotelSummaryDto(any(Hotel.class));
    }

    @Test
    void createHotel_ShouldSaveHotelAndReturnSummaryDto() {
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        when(hotelMapper.toHotelSummaryDto(eq(hotel))).thenReturn(hotelSummaryDto);


        HotelSummaryDto result = hotelService.createHotel(createHotelDto);


        assertEquals(hotelSummaryDto, result);
        verify(hotelRepository, times(1)).save(any(Hotel.class));
        verify(hotelMapper, times(1)).toHotelSummaryDto(eq(hotel));


        verify(hotelRepository).save(hotelCaptor.capture());
        Hotel savedHotel = hotelCaptor.getValue();
        assertEquals("Test Hotel", savedHotel.getName());
        assertEquals("Test Description", savedHotel.getDescription());
        assertEquals("Test Brand", savedHotel.getBrand());
        assertEquals("123-456-7890", savedHotel.getPhone());
        assertEquals("test@hotel.com", savedHotel.getEmail());
        assertEquals(LocalTime.of(14, 0), savedHotel.getCheckInTime());
        assertEquals(LocalTime.of(12, 0), savedHotel.getCheckOutTime());
    }

    @Test
    void createHotel_WithNullCheckOutTime_ShouldHandleCorrectly() {
        hotelArrivalTime.setCheckOut(null);
        createHotelDto.setArrivalTime(hotelArrivalTime);

        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        when(hotelMapper.toHotelSummaryDto(eq(hotel))).thenReturn(hotelSummaryDto);


        HotelSummaryDto result = hotelService.createHotel(createHotelDto);


        assertEquals(hotelSummaryDto, result);
        verify(hotelRepository).save(hotelCaptor.capture());
        Hotel savedHotel = hotelCaptor.getValue();
        assertEquals(LocalTime.of(14, 0), savedHotel.getCheckInTime());
        assertNull(savedHotel.getCheckOutTime());
    }

    @Test
    void getHotelById_WithValidId_ShouldReturnHotelDto() {

        long hotelId = 1L;
        when(hotelRepository.findByIdWithAmenities(hotelId)).thenReturn(Optional.of(hotel));
        when(hotelMapper.toHotelDto(eq(hotel))).thenReturn(hotelDto);


        HotelDto result = hotelService.getHotelById(hotelId);


        assertEquals(hotelDto, result);
        verify(hotelRepository, times(1)).findByIdWithAmenities(hotelId);
        verify(hotelMapper, times(1)).toHotelDto(eq(hotel));
    }

    @Test
    void getHotelById_WithInvalidId_ShouldThrowHotelNotFoundException() {
        long invalidId = -1L;
        when(hotelRepository.findByIdWithAmenities(invalidId)).thenReturn(Optional.empty());


        assertThrows(HotelNotFoundException.class, () -> hotelService.getHotelById(invalidId));
        verify(hotelRepository, times(1)).findByIdWithAmenities(invalidId);
    }

    @Test
    void addAmenitiesToHotel_WithValidAmenities_ShouldAddAmenitiesToHotel() {
        long hotelId = 1L;
        List<String> amenitiesList = List.of("WiFi", "Pool");

        Amenity existingAmenity = new Amenity();
        existingAmenity.setId(1L);
        existingAmenity.setName("WiFi");

        Amenity newAmenity = new Amenity();
        newAmenity.setId(2L);
        newAmenity.setName("Pool");

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        when(amenityRepository.findByNameIgnoreCase("WiFi")).thenReturn(existingAmenity);
        when(amenityRepository.findByNameIgnoreCase("Pool")).thenReturn(null);
        when(amenityRepository.save(any(Amenity.class))).thenReturn(newAmenity);


        hotelService.addAmenitiesToHotel(hotelId, amenitiesList);


        verify(hotelRepository, times(1)).findById(hotelId);
        verify(amenityRepository, times(1)).findByNameIgnoreCase("WiFi");
        verify(amenityRepository, times(1)).findByNameIgnoreCase("Pool");
        verify(amenityRepository, times(1)).save(any(Amenity.class));
        verify(hotelRepository, times(1)).save(any(Hotel.class));


        verify(hotelRepository).save(hotelCaptor.capture());
        Hotel savedHotel = hotelCaptor.getValue();
        assertEquals(2, savedHotel.getAmenities().size());
    }

    @Test
    void addAmenitiesToHotel_WithEmptyAmenitiesList_ShouldNotCallAnyMethods() {
        long hotelId = 1L;
        List<String> emptyAmenitiesList = Collections.emptyList();


        hotelService.addAmenitiesToHotel(hotelId, emptyAmenitiesList);


        verify(hotelRepository, never()).findById(anyLong());
        verify(amenityRepository, never()).findByNameIgnoreCase(anyString());
        verify(amenityRepository, never()).save(any(Amenity.class));
        verify(hotelRepository, never()).save(any(Hotel.class));
    }

    @Test
    void addAmenitiesToNonExistentHotel_ShouldThrowHotelNotFoundException() {
        long invalidHotelId = -1L;
        List<String> amenitiesList = List.of("WiFi");

        when(hotelRepository.findById(invalidHotelId)).thenReturn(Optional.empty());


        assertThrows(HotelNotFoundException.class, () ->
                hotelService.addAmenitiesToHotel(invalidHotelId, amenitiesList));
        verify(hotelRepository, times(1)).findById(invalidHotelId);
    }

    @Test
    void addAmenitiesToHotel_WithOnlyExistingAmenities_ShouldNotCreateNewAmenities() {
        long hotelId = 1L;
        List<String> amenitiesList = List.of("WiFi", "Pool");

        Amenity wifiAmenity = new Amenity();
        wifiAmenity.setId(1L);
        wifiAmenity.setName("WiFi");

        Amenity poolAmenity = new Amenity();
        poolAmenity.setId(2L);
        poolAmenity.setName("Pool");

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        when(amenityRepository.findByNameIgnoreCase("WiFi")).thenReturn(wifiAmenity);
        when(amenityRepository.findByNameIgnoreCase("Pool")).thenReturn(poolAmenity);


        hotelService.addAmenitiesToHotel(hotelId, amenitiesList);


        verify(hotelRepository, times(1)).findById(hotelId);
        verify(amenityRepository, times(2)).findByNameIgnoreCase(anyString());
        verify(amenityRepository, never()).save(any(Amenity.class));
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    void searchHotels_WithAmenitiesAndOtherFilters_ShouldApplyAllFilters() {
        String name = "Test";
        String brand = "Test Brand";
        String city = "New York";
        String country = "USA";
        List<String> amenities = List.of("wifi", "pool");

        Hotel matchingHotel = new Hotel();
        matchingHotel.setId(1L);
        matchingHotel.setName("Test Hotel");
        matchingHotel.setBrand("Test Brand");
        matchingHotel.setAddress(address);

        List<Hotel> filteredHotels = List.of(matchingHotel);

        when(hotelRepository.findByAmenitiesAllMatch(anyList(), anyInt())).thenReturn(filteredHotels);
        when(hotelMapper.toHotelSummaryDto(any(Hotel.class))).thenReturn(hotelSummaryDto);


        List<HotelSummaryDto> result = hotelService.searchHotels(name, brand, city, country, amenities);


        assertEquals(1, result.size());
        verify(hotelRepository, times(1)).findByAmenitiesAllMatch(anyList(), anyInt());
        verify(hotelMapper, times(1)).toHotelSummaryDto(any(Hotel.class));
    }

    @Test
    void searchHotels_WithAmenitiesButNoOtherFilters_ShouldOnlyFilterByAmenities() {
        String name = null;
        String brand = null;
        String city = null;
        String country = null;
        List<String> amenities = List.of("wifi", "pool");

        List<Hotel> filteredHotels = List.of(hotel);

        when(hotelRepository.findByAmenitiesAllMatch(anyList(), anyInt())).thenReturn(filteredHotels);
        when(hotelMapper.toHotelSummaryDto(any(Hotel.class))).thenReturn(hotelSummaryDto);


        List<HotelSummaryDto> result = hotelService.searchHotels(name, brand, city, country, amenities);


        assertEquals(1, result.size());
        verify(hotelRepository, times(1)).findByAmenitiesAllMatch(anyList(), anyInt());
        verify(hotelMapper, times(1)).toHotelSummaryDto(any(Hotel.class));
    }

    @Test
    void searchHotels_WithNoAmenities_ShouldUseSimpleSearch() {
        String name = "Test";
        String brand = "Test Brand";
        String city = "New York";
        String country = "USA";
        List<String> amenities = null;

        List<Hotel> searchedHotels = List.of(hotel);

        when(hotelRepository.searchHotels(name, brand, city, country)).thenReturn(searchedHotels);
        when(hotelMapper.toHotelSummaryDto(any(Hotel.class))).thenReturn(hotelSummaryDto);


        List<HotelSummaryDto> result = hotelService.searchHotels(name, brand, city, country, amenities);


        assertEquals(1, result.size());
        verify(hotelRepository, times(1)).searchHotels(name, brand, city, country);
        verify(hotelRepository, never()).findByAmenitiesAllMatch(anyList(), anyInt());
        verify(hotelMapper, times(1)).toHotelSummaryDto(any(Hotel.class));
    }

    @Test
    void searchHotels_WithEmptyAmenitiesList_ShouldUseSimpleSearch() {
        String name = "Test";
        String brand = "Test Brand";
        String city = "New York";
        String country = "USA";
        List<String> amenities = Collections.emptyList();

        List<Hotel> searchedHotels = List.of(hotel);

        when(hotelRepository.searchHotels(name, brand, city, country)).thenReturn(searchedHotels);
        when(hotelMapper.toHotelSummaryDto(any(Hotel.class))).thenReturn(hotelSummaryDto);


        List<HotelSummaryDto> result = hotelService.searchHotels(name, brand, city, country, amenities);


        assertEquals(1, result.size());
        verify(hotelRepository, times(1)).searchHotels(name, brand, city, country);
        verify(hotelRepository, never()).findByAmenitiesAllMatch(anyList(), anyInt());
        verify(hotelMapper, times(1)).toHotelSummaryDto(any(Hotel.class));
    }

    @Test
    void searchHotels_WithAmenitiesAndNameFilter_ShouldApplyBothFilters() {
        String name = "Test";
        String brand = null;
        String city = null;
        String country = null;
        List<String> amenities = List.of("wifi", "pool");

        Hotel matchingHotel = new Hotel();
        matchingHotel.setId(1L);
        matchingHotel.setName("Test Hotel");
        matchingHotel.setBrand("Test Brand");
        matchingHotel.setAddress(address);

        Hotel nonMatchingHotel = new Hotel();
        nonMatchingHotel.setId(2L);
        nonMatchingHotel.setName("Other Hotel");
        nonMatchingHotel.setBrand("Other Brand");
        nonMatchingHotel.setAddress(address);

        List<Hotel> initialFilteredHotels = List.of(matchingHotel, nonMatchingHotel);

        when(hotelRepository.findByAmenitiesAllMatch(anyList(), anyInt())).thenReturn(initialFilteredHotels);
        when(hotelMapper.toHotelSummaryDto(any(Hotel.class))).thenReturn(hotelSummaryDto);


        List<HotelSummaryDto> result = hotelService.searchHotels(name, brand, city, country, amenities);


        assertEquals(1, result.size());
        verify(hotelRepository, times(1)).findByAmenitiesAllMatch(anyList(), anyInt());
        verify(hotelMapper, times(1)).toHotelSummaryDto(any(Hotel.class));
    }

    @Test
    void searchHotels_WithAmenitiesAndEmptyNameFilter_ShouldIgnoreEmptyNameFilter() {
        String name = "";
        String brand = null;
        String city = null;
        String country = null;
        List<String> amenities = List.of("wifi", "pool");

        Hotel matchingHotel = new Hotel();
        matchingHotel.setId(1L);
        matchingHotel.setName("Test Hotel");
        matchingHotel.setBrand("Test Brand");
        matchingHotel.setAddress(address);

        List<Hotel> initialFilteredHotels = List.of(matchingHotel);

        when(hotelRepository.findByAmenitiesAllMatch(anyList(), anyInt())).thenReturn(initialFilteredHotels);
        when(hotelMapper.toHotelSummaryDto(any(Hotel.class))).thenReturn(hotelSummaryDto);


        List<HotelSummaryDto> result = hotelService.searchHotels(name, brand, city, country, amenities);


        assertEquals(1, result.size());
        verify(hotelRepository, times(1)).findByAmenitiesAllMatch(anyList(), anyInt());
        verify(hotelMapper, times(1)).toHotelSummaryDto(any(Hotel.class));
    }

    @Test
    void searchHotels_WithNullAllParameters_ShouldReturnEmptyList() {
        when(hotelRepository.searchHotels(null, null, null, null)).thenReturn(Collections.emptyList());


        List<HotelSummaryDto> result = hotelService.searchHotels(null, null, null, null, null);


        assertTrue(result.isEmpty());
        verify(hotelRepository, times(1)).searchHotels(null, null, null, null);
    }

    @Test
    void getHistogram_WithValidBrandParam_ShouldReturnBrandHistogram() {
        String param = "brand";
        List<HistogramElementDto> mockResults = List.of(
                new HistogramElementDto("Brand A", 5L),
                new HistogramElementDto("Brand B", 3L)
        );
        Map<String, Long> expectedMap = Map.of("Brand A", 5L, "Brand B", 3L);

        when(hotelRepository.countByBrand()).thenReturn(mockResults);
        when(hotelMapper.toHistogramMap(anyList())).thenReturn(expectedMap);


        Map<String, Long> result = hotelService.getHistogram(param);


        assertEquals(expectedMap, result);
        verify(hotelRepository, times(1)).countByBrand();
        verify(hotelMapper, times(1)).toHistogramMap(anyList());
    }

    @Test
    void getHistogram_WithValidCityParam_ShouldReturnCityHistogram() {
        String param = "city";
        List<HistogramElementDto> mockResults = List.of(
                new HistogramElementDto("New York", 10L),
                new HistogramElementDto("London", 5L)
        );
        Map<String, Long> expectedMap = Map.of("New York", 10L, "London", 5L);

        when(hotelRepository.countByCity()).thenReturn(mockResults);
        when(hotelMapper.toHistogramMap(anyList())).thenReturn(expectedMap);


        Map<String, Long> result = hotelService.getHistogram(param);


        assertEquals(expectedMap, result);
        verify(hotelRepository, times(1)).countByCity();
        verify(hotelMapper, times(1)).toHistogramMap(anyList());
    }

    @Test
    void getHistogram_WithValidCountryParam_ShouldReturnCountryHistogram() {
        String param = "country";
        List<HistogramElementDto> mockResults = List.of(
                new HistogramElementDto("USA", 15L),
                new HistogramElementDto("UK", 8L)
        );
        Map<String, Long> expectedMap = Map.of("USA", 15L, "UK", 8L);

        when(hotelRepository.countByCountry()).thenReturn(mockResults);
        when(hotelMapper.toHistogramMap(anyList())).thenReturn(expectedMap);


        Map<String, Long> result = hotelService.getHistogram(param);


        assertEquals(expectedMap, result);
        verify(hotelRepository, times(1)).countByCountry();
        verify(hotelMapper, times(1)).toHistogramMap(anyList());
    }

    @Test
    void getHistogram_WithValidAmenitiesParam_ShouldReturnAmenitiesHistogram() {
        String param = "amenities";
        List<HistogramElementDto> mockResults = List.of(
                new HistogramElementDto("WiFi", 20L),
                new HistogramElementDto("Pool", 12L)
        );
        Map<String, Long> expectedMap = Map.of("WiFi", 20L, "Pool", 12L);

        when(hotelRepository.countByAmenity()).thenReturn(mockResults);
        when(hotelMapper.toHistogramMap(anyList())).thenReturn(expectedMap);


        Map<String, Long> result = hotelService.getHistogram(param);


        assertEquals(expectedMap, result);
        verify(hotelRepository, times(1)).countByAmenity();
        verify(hotelMapper, times(1)).toHistogramMap(anyList());
    }

    @Test
    void getHistogram_WithInvalidParam_ShouldThrowInvalidHistogramParamException() {
        String invalidParam = "invalid_param";


        assertThrows(InvalidHistogramParamException.class, () ->
                hotelService.getHistogram(invalidParam));
    }
}
