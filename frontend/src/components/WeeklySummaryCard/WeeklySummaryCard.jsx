import React from 'react';
import WeatherIcon from '../WeatherIcon/WeatherIcon';
import WeatherInfo from '../WeatherInfo/WeatherInfo';
import pressure from '../../assets/icons/pressure.svg';
import exposure from '../../assets/icons/exposure.svg';
import temperature from '../../assets/icons/temperature.svg';
import withPrecipitation from '../../assets/icons/withPrecipitation.svg';
import withoutPrecipitation from '../../assets/icons/withoutPrecipitation.svg';
import './WeeklySummaryCard.css';

function WeeklySummaryCard() {
    return (
        <div className="weekly-weather-summary">
            <div className="weather-column">
                <div className="weather-item">
                    <WeatherIcon src={pressure} />
                    <WeatherInfo label="Average pressure" value="NaN hPa" />
                </div>
                <div className="weather-item">
                    <WeatherIcon src={exposure} />
                    <WeatherInfo label="Average Sun exposure time" value="NaN h NaN min" />
                </div>
            </div>
            <div className="weather-column">
                <div className="weather-item">
                    <WeatherIcon src={temperature} />
                    <WeatherInfo label="Temperature Range" value="NaN ° - NaN °" />
                </div>
                <div className="weather-item">
                    <WeatherIcon src={withPrecipitation} />
                    <WeatherInfo label="Predicted Weather Conditions" value="Unknown" />
                </div>
            </div>
        </div>
    );
}

export default WeeklySummaryCard;