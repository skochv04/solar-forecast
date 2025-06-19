package com.example.backend;

import com.example.backend.dto.*;
import com.example.backend.exception.ExternalApiException;
import com.example.backend.service.ForecastService;
import com.example.backend.service.WeatherSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ForecastServiceTest {

    private RestTemplate restTemplate;
    private ForecastService forecastService;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        forecastService = new ForecastService(restTemplate);
    }

    // Unit-Tests

    @Test
    void convertSecondsToHours_shouldConvertCorrectly() {
        // Given
        double seconds = 7200;

        // When
        double hours = forecastService.convertSecondsToHours(seconds);

        // Then
        assertEquals(2.0, hours);
    }

    @Test
    void calculateEstimatedEnergy_shouldCalculateRounded() {
        // Given
        double seconds = 3600; // 1h

        // When
        double energy = forecastService.calculateEstimatedEnergy(seconds);

        // Then
        assertEquals(0.5, energy);
    }

    @Test
    void calculateAverage_shouldReturnCorrectAverage() {
        // Given
        List<Double> values = List.of(10.0, 20.0, 30.0);

        // When
        double avg = forecastService.calculateAverage(values);

        // Then
        assertEquals(20.0, avg);
    }

    @Test
    void calculateAverage_shouldThrowExceptionOnEmptyList() {
        // Given
        List<Double> values = List.of();

        // When + Then
        assertThrows(IllegalArgumentException.class, () -> forecastService.calculateAverage(values));
    }

    @Test
    void calculateAverage_shouldThrowExceptionOnNull() {
        assertThrows(IllegalArgumentException.class, () -> forecastService.calculateAverage(null));
    }

    @Test
    void summarizeWeather_shouldReturnWITH_PRECIPITATION_WhenManyRainyDays() {
        // Given
        List<Integer> probabilities = List.of(60, 70, 80, 50, 20, 30, 10);

        // When
        WeatherSummary result = forecastService.summarizeWeather(probabilities, 7);

        // Then
        assertEquals(WeatherSummary.WITH_PRECIPITATION, result);
    }

    @Test
    void summarizeWeather_shouldReturnWITHOUT_PRECIPITATION_WhenFewRainyDays() {
        // Given
        List<Integer> probabilities = List.of(10, 20, 30, 40, 10, 20, 30);

        // When
        WeatherSummary result = forecastService.summarizeWeather(probabilities, 7);

        // Then
        assertEquals(WeatherSummary.WITHOUT_PRECIPITATION, result);
    }

    @Test
    void summarizeWeather_shouldThrowOnNullOrEmptyList() {
        // Then
        assertThrows(IllegalArgumentException.class, () -> forecastService.summarizeWeather(null, 7));
        assertThrows(IllegalArgumentException.class, () -> forecastService.summarizeWeather(List.of(), 7));
    }

    // Unit-Tests: mocking RestTemplate

    @Test
    void getWeatherData_shouldReturnDataFromApi() {
        // Given
        OpenMeteoResponse expected = new OpenMeteoResponse();
        when(restTemplate.getForObject(anyString(), eq(OpenMeteoResponse.class))).thenReturn(expected);

        // When
        OpenMeteoResponse result = forecastService.getWeatherData(50.0, 20.0);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void getWeatherData_shouldThrowExternalApiException_OnServerError() {
        when(restTemplate.getForObject(anyString(), eq(OpenMeteoResponse.class)))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(ExternalApiException.class, () -> forecastService.getWeatherData(50, 20));
    }

    @Test
    void getWeatherData_shouldThrowExternalApiException_OnConnectionFailure() {
        when(restTemplate.getForObject(anyString(), eq(OpenMeteoResponse.class)))
                .thenThrow(new ResourceAccessException("Connection refused"));

        assertThrows(ExternalApiException.class, () -> forecastService.getWeatherData(50, 20));
    }

    @Test
    void getWeatherData_shouldThrowExternalApiException_OnHttpError() {
        // Given
        HttpClientErrorException exception = new HttpClientErrorException(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                new HttpHeaders(),
                null,
                null
        );
        when(restTemplate.getForObject(anyString(), eq(OpenMeteoResponse.class)))
                .thenThrow(exception);

        // Then
        assertThrows(ExternalApiException.class, () -> forecastService.getWeatherData(50, 20));
    }

    // Integration-style test

    @Test
    void getWeeklyForecast_shouldBuildDailyForecastsCorrectly() {
        // Given
        OpenMeteoResponse.Daily daily = new OpenMeteoResponse.Daily();
        daily.setTime(List.of("2024-06-01"));
        daily.setWeather_code(List.of(1));
        daily.setTemperature_2m_min(List.of(10.0));
        daily.setTemperature_2m_max(List.of(20.0));
        daily.setSunshine_duration(List.of(3600.0));

        OpenMeteoResponse response = new OpenMeteoResponse();
        response.setDaily(daily);

        when(restTemplate.getForObject(anyString(), eq(OpenMeteoResponse.class))).thenReturn(response);

        // When
        WeeklyForecastResponse result = forecastService.getWeeklyForecast(50, 20);

        // Then
        assertEquals(1, result.dailyForecasts().size());
        DailyForecast df = result.dailyForecasts().getFirst();
        assertEquals(LocalDate.of(2024, 6, 1), df.getDate());
        assertEquals(1, df.getWeatherCode());
        assertEquals(10.0, df.getMinTemperaturePerDay());
        assertEquals(20.0, df.getMaxTemperaturePerDay());
        assertEquals(0.5, df.getEstimatedEnergy());
    }

    @Test
    void getWeeklyForecastSummary_shouldReturnSummary() {
        // Given
        OpenMeteoResponse.Daily daily = new OpenMeteoResponse.Daily();
        daily.setTemperature_2m_min(List.of(10.0, 12.0));
        daily.setTemperature_2m_max(List.of(20.0, 22.0));
        daily.setPrecipitation_probability_max(List.of(10, 80));
        daily.setSunshine_duration(new ArrayList<>(List.of(3600.0, 7200.0)));

        OpenMeteoResponse.Hourly hourly = new OpenMeteoResponse.Hourly();
        hourly.setPressure_msl(List.of(1015.0, 1013.0));

        OpenMeteoResponse response = new OpenMeteoResponse();
        response.setDaily(daily);
        response.setHourly(hourly);

        when(restTemplate.getForObject(anyString(), eq(OpenMeteoResponse.class))).thenReturn(response);

        // When
        WeeklyForecastSummaryResponse summary = forecastService.getWeeklyForecastSummary(50, 20);

        // Then
        assertEquals(11.0, summary.minTemperaturePerWeek());
        assertEquals(21.0, summary.maxTemperaturePerWeek());
        assertEquals(1014.0, summary.averagePressurePerWeek());
        assertEquals(1.5, summary.averageSunExposurePerWeek());
        assertEquals(WeatherSummary.WITHOUT_PRECIPITATION, summary.weatherSummary());
    }

    @Test
    void getWeeklyForecastSummary_shouldThrowOnIncompleteData() {
        OpenMeteoResponse response = new OpenMeteoResponse();
        response.setDaily(new OpenMeteoResponse.Daily()); // missing everything
        response.setHourly(new OpenMeteoResponse.Hourly());

        when(restTemplate.getForObject(anyString(), eq(OpenMeteoResponse.class))).thenReturn(response);

        assertThrows(IllegalArgumentException.class,
                () -> forecastService.getWeeklyForecastSummary(0, 0));
    }

    @Test
    void getWeeklyForecast_shouldThrowOnIncompleteData() {
        // Given
        OpenMeteoResponse response = new OpenMeteoResponse();
        response.setDaily(new OpenMeteoResponse.Daily()); // all fields null

        when(restTemplate.getForObject(anyString(), eq(OpenMeteoResponse.class))).thenReturn(response);

        // Then
        assertThrows(IllegalArgumentException.class, () -> forecastService.getWeeklyForecast(0, 0));
    }
}
