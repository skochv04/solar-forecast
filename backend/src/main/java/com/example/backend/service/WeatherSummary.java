package com.example.backend.service;
public enum WeatherSummary {
    WITH_PRECIPITATION("With precipitation"),
    WITHOUT_PRECIPITATION("Without precipitation");

    private final String description;

    WeatherSummary(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}