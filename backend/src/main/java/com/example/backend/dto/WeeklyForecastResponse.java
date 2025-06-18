package com.example.backend.dto;

import java.util.List;

public record WeeklyForecastResponse(List<DailyForecast> dailyForecasts) { }