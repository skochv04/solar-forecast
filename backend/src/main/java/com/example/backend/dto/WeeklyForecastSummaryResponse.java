package com.example.backend.dto;

import com.example.backend.service.WeatherSummary;

public record WeeklyForecastSummaryResponse(double minTemperaturePerWeek, double maxTemperaturePerWeek,
                                            double averagePressurePerWeek, double averageSunExposurePerWeek, WeatherSummary weatherSummary) {

}