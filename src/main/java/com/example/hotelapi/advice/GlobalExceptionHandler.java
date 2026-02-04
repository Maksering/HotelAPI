package com.example.hotelapi.advice;

import com.example.hotelapi.exception.InvalidHistogramParamException;
import com.example.hotelapi.exception.HotelNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleHotelNotFound(HotelNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidHistogramParamException.class)
    public ResponseEntity<Map<String, String>> handleInvalidHistogramPara(InvalidHistogramParamException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
