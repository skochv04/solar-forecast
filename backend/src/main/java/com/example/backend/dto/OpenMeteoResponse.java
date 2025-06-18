package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class OpenMeteoResponse {
    private Daily daily;
    private Hourly hourly;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Daily {
        private List<String> time;
        private List<Double> temperature_2m_max;
        private List<Double> temperature_2m_min;
        private List<Integer> weather_code;
        private List<Double> sunshine_duration;
        private List<Integer> precipitation_probability_max;

        public List<String> getTime() {
            return time;
        }

        public void setTime(List<String> time) {
            this.time = time;
        }

        public List<Double> getTemperature_2m_max() {
            return temperature_2m_max;
        }

        public void setTemperature_2m_max(List<Double> temperature_2m_max) {
            this.temperature_2m_max = temperature_2m_max;
        }

        public List<Double> getTemperature_2m_min() {
            return temperature_2m_min;
        }

        public void setTemperature_2m_min(List<Double> temperature_2m_min) {
            this.temperature_2m_min = temperature_2m_min;
        }

        public List<Integer> getWeather_code() {
            return weather_code;
        }

        public void setWeather_code(List<Integer> weather_code) {
            this.weather_code = weather_code;
        }

        public List<Double> getSunshine_duration() {
            return sunshine_duration;
        }

        public void setSunshine_duration(List<Double> sunshine_duration) {
            this.sunshine_duration = sunshine_duration;
        }

        public List<Integer> getPrecipitation_probability_max() {
            return precipitation_probability_max;
        }

        public void setPrecipitation_probability_max(List<Integer> precipitation_probability_max) {
            this.precipitation_probability_max = precipitation_probability_max;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hourly {
        private List<Double> pressure_msl;

        public List<Double> getPressure_msl() {
            return pressure_msl;
        }

        public void setPressure_msl(List<Double> pressure_msl) {
            this.pressure_msl = pressure_msl;
        }
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    public Hourly getHourly() {
        return hourly;
    }

    public void setHourly(Hourly hourly) {
        this.hourly = hourly;
    }
}