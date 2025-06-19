import React from 'react';
import WeatherIcon from '../WeatherIcon/WeatherIcon';
import WeatherInfo from '../WeatherInfo/WeatherInfo';

import pressure from '../../assets/icons/pressure.svg';

import './DailyCard.css';

function DailyCard({ date, dayName, energy, minTemp, maxTemp }) {
    return (
        <div className="daily-card">
            <div className="daily-card-item with-icon">
                <WeatherIcon src={pressure} />
                <WeatherInfo label={new Date(date).toLocaleDateString("pl-PL")} value={dayName?.slice(0, 3)} reverse={true}/>
            </div>
            <div className="daily-card-item">
                <WeatherInfo label="Estimated energy" value={`${energy ?? "NaN"} kWh`}/>
            </div>
            <div className="daily-card-item">
                <WeatherInfo label="Temperature range" value={`${minTemp ?? "NaN"}° - ${maxTemp ?? "NaN"}°`}/>
            </div>
        </div>
    );
}


export default DailyCard;
