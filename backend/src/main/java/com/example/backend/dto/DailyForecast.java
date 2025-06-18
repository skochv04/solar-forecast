package com.example.backend.dto;

import java.time.LocalDate;

public class DailyForecast {

    private final LocalDate date;
    private final int weatherCode;
    private final double minTemperaturePerDay;
    private final double maxTemperaturePerDay;
    private final double estimatedEnergy; // kWh

    public DailyForecast(LocalDate date, int weatherCode, double minTemperaturePerDay,
                         double maxTemperaturePerDay, double estimatedEnergy) {
        this.date = date;
        this.weatherCode = weatherCode;
        this.minTemperaturePerDay = minTemperaturePerDay;
        this.maxTemperaturePerDay = maxTemperaturePerDay;
        this.estimatedEnergy = estimatedEnergy;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getWeatherCode() {
        return weatherCode;
    }

    public double getMinTemperaturePerDay() {
        return minTemperaturePerDay;
    }

    public double getMaxTemperaturePerDay() {
        return maxTemperaturePerDay;
    }

    public double getEstimatedEnergy() {
        return estimatedEnergy;
    }
}
