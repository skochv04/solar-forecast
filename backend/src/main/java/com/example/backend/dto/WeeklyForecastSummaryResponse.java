package com.example.backend.dto;

import com.example.backend.service.WeatherSummary;

public class WeeklyForecastSummaryResponse {

    private final double minTemperaturePerWeek;
    private final double maxTemperaturePerWeek;
    private final double averagePressurePerWeek;
    private final WeatherSummary weatherSummary;

    public WeeklyForecastSummaryResponse(double minTemperaturePerWeek, double maxTemperaturePerWeek, double averagePressurePerWeek, WeatherSummary weatherSummary) {
        this.minTemperaturePerWeek = minTemperaturePerWeek;
        this.maxTemperaturePerWeek = maxTemperaturePerWeek;
        this.averagePressurePerWeek = averagePressurePerWeek;
        this.weatherSummary = weatherSummary;
    }

    public double getMinTemperaturePerWeek() {
        return minTemperaturePerWeek;
    }

    public double getMaxTemperaturePerWeek() {
        return maxTemperaturePerWeek;
    }

    public double getAveragePressurePerWeek() {
        return averagePressurePerWeek;
    }

    public WeatherSummary getWeatherSummary() {
        return weatherSummary;
    }
}