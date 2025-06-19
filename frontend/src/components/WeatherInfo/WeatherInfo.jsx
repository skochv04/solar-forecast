import React from 'react';
import './WeatherInfo.css';

function WeatherInfo({ label, value, reverse = false }) {
    return (
        <div className="weather-info">
            {reverse ? (
                <>
                    <p className="weather-value">{value}</p>
                    <p className="weather-label">{label}</p>
                </>
            ) : (
                <>
                    <p className="weather-label">{label}</p>
                    <p className="weather-value">{value}</p>
                </>
            )}
        </div>
    );
}

export default WeatherInfo;