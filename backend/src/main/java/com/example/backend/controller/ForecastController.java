package com.example.backend.controller;

import com.example.backend.dto.WeeklyForecastResponse;
import com.example.backend.dto.WeeklyForecastSummaryResponse;
import com.example.backend.service.ForecastService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/forecast")
@CrossOrigin(origins = "http://localhost:3000")
public class ForecastController {

    private final ForecastService forecastService;

    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping("/weekly")
    public WeeklyForecastResponse getWeeklyForecast(@RequestParam double latitude, @RequestParam double longitude) {
        validateCoordinates(latitude, longitude);
        return forecastService.getWeeklyForecast(latitude, longitude);
    }

    @GetMapping("/weekly/summary")
    public WeeklyForecastSummaryResponse getWeeklyForecastSummary(@RequestParam double latitude, @RequestParam double longitude) {
        validateCoordinates(latitude, longitude);
        return forecastService.getWeeklyForecastSummary(latitude, longitude);
    }

    private void validateCoordinates(double latitude, double longitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90.");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180.");
        }
    }
}