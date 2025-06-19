import React from 'react';
import WeatherIcon from '../WeatherIcon/WeatherIcon';
import WeatherInfo from '../WeatherInfo/WeatherInfo';
import pressureIcon from '../../assets/icons/pressure.svg';
import exposureIcon from '../../assets/icons/exposure.svg';
import temperatureIcon from '../../assets/icons/temperature.svg';
import withPrecipitationIcon from '../../assets/icons/withPrecipitation.svg';
import withoutPrecipitationIcon from '../../assets/icons/withoutPrecipitation.svg';
import './WeeklySummaryCard.css';

function WeeklySummaryCard({ minTemp, maxTemp, pressure, sunExposure, summary }) {
    const hours = Math.floor(sunExposure);
    const minutes = Math.round((sunExposure - hours) * 60);

    return (
        <div className="weekly-weather-summary">
            <div className="weather-column">
                <div className="weather-item">
                    <WeatherIcon src={pressureIcon} />
                    <WeatherInfo label="Average pressure" value={`${pressure} hPa`} />
                </div>
                <div className="weather-item">
                    <WeatherIcon src={exposureIcon} />
                    <WeatherInfo label="Average Sun exposure time" value={`${hours} h ${minutes} min`} />
                </div>
            </div>
            <div className="weather-column">
                <div className="weather-item">
                    <WeatherIcon src={temperatureIcon} />
                    <WeatherInfo label="Temperature Range" value={`${minTemp}° / ${maxTemp}°`} />
                </div>
                <div className="weather-item">
                    <WeatherIcon src={summary === 'WITH_PRECIPITATION' ? withPrecipitationIcon : withoutPrecipitationIcon} />
                    <WeatherInfo label="Predicted Weather Conditions" value={summary.replace('_', ' ').toLowerCase()} />
                </div>
            </div>
        </div>
    );
}

export default WeeklySummaryCard;