import React from 'react';
import './WeatherIcon.css';

function WeatherIcon({ src, alt = 'weather icon' }) {
    return (
        <div className="weather-icon-box">
            <img src={src} alt={alt} className="weather-icon" />
        </div>
    );
}

export default WeatherIcon;