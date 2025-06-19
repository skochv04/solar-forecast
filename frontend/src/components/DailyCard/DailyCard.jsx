import React from 'react';
import WeatherIcon from '../WeatherIcon/WeatherIcon';
import WeatherInfo from '../WeatherInfo/WeatherInfo';

import clearDay from '../../assets/icons/clearDay.svg';
import overcastDay from '../../assets/icons/overcastDay.svg';
import fog from '../../assets/icons/fog.svg';
import sleet from '../../assets/icons/sleet.svg';
import drizzle from '../../assets/icons/drizzle.svg';
import rain from '../../assets/icons/rain.svg';
import snow from '../../assets/icons/snow.svg';
import cloudy from '../../assets/icons/cloudy.svg';
import thunderstorms from '../../assets/icons/thunderstorms.svg';
import thunderstormsRain from '../../assets/icons/thunderstorms-rain.svg';

import './DailyCard.css';

function getWeatherIconSrc(code) {
    if (code === 0) return clearDay;
    if ([1, 2, 3].includes(code)) return overcastDay;
    if ([45, 48].includes(code)) return fog;
    if ([51, 53, 55].includes(code)) return drizzle;
    if ([61, 63, 65, 80, 81, 82].includes(code)) return rain;
    if ([56, 57, 66, 67].includes(code)) return sleet;
    if ([71, 73, 75, 77, 85, 86].includes(code)) return snow;
    if (code === 95) return thunderstorms;
    if ([96, 99].includes(code)) return thunderstormsRain;
    return cloudy;
}

function DailyCard({ date, dayName, energy, minTemp, maxTemp, weatherCode }) {
    const weatherIcon = getWeatherIconSrc(weatherCode);

    return (
        <div className="daily-card">
            <div className="daily-card-item with-icon">
                <WeatherIcon src={weatherIcon} />
                <WeatherInfo label={new Date(date).toLocaleDateString("pl-PL")} value={dayName?.slice(0, 3)} reverse={true}/>
            </div>
            <div className="daily-card-item">
                <WeatherInfo label="Estimated energy" value={`${energy ?? "NaN"} kWh`}/>
            </div>
            <div className="daily-card-item">
                <WeatherInfo label="Temperature range" value={`${minTemp ?? "NaN"}° / ${maxTemp ?? "NaN"}°`}/>
            </div>
        </div>
    );
}


export default DailyCard;
