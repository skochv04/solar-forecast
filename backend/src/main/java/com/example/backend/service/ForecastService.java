package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.exception.ExternalApiException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ForecastService {
    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";

    public static final int FORECAST_DAYS = 7;
    public static final double SOLAR_SYSTEM_POWER = 2.5;  // kW
    public static final double PANEL_EFFICIENCY = 0.2;   // efficiency (20%)
    private final RestTemplate restTemplate;

    public ForecastService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double convertSecondsToHours(double seconds) {
        return seconds / 3600.0;
    }

    public OpenMeteoResponse getWeatherData(double latitude, double longitude) {

        String apiUrl = String.format(Locale.US,
                "%s?latitude=%f&longitude=%f&daily=temperature_2m_max,temperature_2m_min," +
                        "weather_code,sunshine_duration,precipitation_probability_max" +
                        "&hourly=pressure_msl&timezone=auto&forecast_days=%d",
                BASE_URL, latitude, longitude, FORECAST_DAYS
        );


        try {
            return restTemplate.getForObject(apiUrl, OpenMeteoResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ExternalApiException("Open-Meteo server error: " + e.getStatusCode(), e);
        } catch (ResourceAccessException e) {
            throw new ExternalApiException("Unable to connect to Open-Meteo server", e);
        } catch (RestClientException e) {
            throw new ExternalApiException("Unexpected error while fetching data from Open-Meteo", e);
        }
    }

    public double calculateEstimatedEnergy(double exposureTimeSeconds){
        double exposureTimeHours = convertSecondsToHours(exposureTimeSeconds);
        double estimatedEnergy = SOLAR_SYSTEM_POWER * exposureTimeHours * PANEL_EFFICIENCY;
        return Math.round(estimatedEnergy * 100.0) / 100.0;
    }

    public double calculateAverage(List<Double> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("Values list is empty or null");
        }

        double sum = 0;
        for (Double p : values) {
            sum += p;
        }

        double average = sum / values.size();

        return Math.round(average * 100.0) / 100.0;
    }

    public WeatherSummary summarizeWeather(List<Integer> precipitationProbabilities, int days){
        if (precipitationProbabilities == null || precipitationProbabilities.isEmpty()) {
            throw new IllegalArgumentException("Precipitation probabilities list is empty or null");
        }
        int daysWithPrecipitation = 0;
        for (Integer p : precipitationProbabilities) {
            if (p != null && p >= 50) {
                daysWithPrecipitation++;
            }
        }

        return daysWithPrecipitation > (days / 2)
                ? WeatherSummary.WITH_PRECIPITATION
                : WeatherSummary.WITHOUT_PRECIPITATION;
    }

    private boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public WeeklyForecastResponse getWeeklyForecast(double latitude, double longitude) {
        OpenMeteoResponse weatherData = getWeatherData(latitude, longitude);
        OpenMeteoResponse.Daily daily = weatherData.getDaily();

        if (daily == null
                || isEmpty(daily.getTime())
                || isEmpty(daily.getWeather_code())
                || isEmpty(daily.getTemperature_2m_min())
                || isEmpty(daily.getTemperature_2m_max())
                || isEmpty(daily.getSunshine_duration())) {
            throw new IllegalArgumentException("Incomplete or missing daily weather data from Open-Meteo API");
        }

        List<String> times = daily.getTime();
        List<Integer> weatherCodes = daily.getWeather_code();
        List<Double> minTemps = daily.getTemperature_2m_min();
        List<Double> maxTemps = daily.getTemperature_2m_max();
        List<Double> sunshineDurations = daily.getSunshine_duration();

        List<DailyForecast> dailyForecasts = new ArrayList<>();

        for (int i = 0; i < times.size(); i++) {
            double estimatedEnergy = calculateEstimatedEnergy(sunshineDurations.get(i));

            DailyForecast forecast = new DailyForecast(
                    LocalDate.parse(times.get(i)),
                    weatherCodes.get(i),
                    minTemps.get(i),
                    maxTemps.get(i),
                    estimatedEnergy
            );

            dailyForecasts.add(forecast);
        }

        return new WeeklyForecastResponse(dailyForecasts);
    }

    public WeeklyForecastSummaryResponse getWeeklyForecastSummary(double latitude, double longitude) {
        OpenMeteoResponse weatherData = getWeatherData(latitude, longitude);
        OpenMeteoResponse.Daily daily = weatherData.getDaily();

        if (daily == null
                || isEmpty(daily.getTemperature_2m_min())
                || isEmpty(daily.getTemperature_2m_max())
                || isEmpty(daily.getPrecipitation_probability_max())
                || isEmpty(daily.getSunshine_duration())) {
            throw new IllegalArgumentException("Incomplete daily summary data from Open-Meteo API");
        }

        List<Double> minTemps = daily.getTemperature_2m_min();
        List<Double> maxTemps = daily.getTemperature_2m_max();
        List<Integer> precipitationProbabilities = daily.getPrecipitation_probability_max();
        List<Double> sunshineDurations = daily.getSunshine_duration();
        sunshineDurations.replaceAll(this::convertSecondsToHours);

        double minTemperaturePerWeek = calculateAverage(minTemps);
        double maxTemperaturePerWeek = calculateAverage(maxTemps);
        double averageSunExposurePerWeek = calculateAverage(sunshineDurations);

        OpenMeteoResponse.Hourly hourly = weatherData.getHourly();
        List<Double> pressure_msl = hourly.getPressure_msl();
        double averagePressurePerWeek = calculateAverage(pressure_msl);

        WeatherSummary weatherSummary = summarizeWeather(precipitationProbabilities, FORECAST_DAYS);

        return new WeeklyForecastSummaryResponse(
                minTemperaturePerWeek,
                maxTemperaturePerWeek,
                averagePressurePerWeek,
                averageSunExposurePerWeek,
                weatherSummary
        );
    }

}