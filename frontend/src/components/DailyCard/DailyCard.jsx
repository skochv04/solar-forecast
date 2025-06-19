import React from 'react';
import WeatherIcon from '../WeatherIcon/WeatherIcon';
import WeatherInfo from '../WeatherInfo/WeatherInfo';

import pressure from '../../assets/icons/pressure.svg';

import './DailyCard.css';

function DailyCard() {
    return (
        <div className="daily-card">
            <div className="daily-card-item with-icon">
                <WeatherIcon src={pressure} />
                <WeatherInfo label="30.06.2025" value="Thursday" reverse={true}/>
            </div>
            <div className="daily-card-item">
                <WeatherInfo label="Estimated energy" value="NaN kWh" />
            </div>
            <div className="daily-card-item">
                <WeatherInfo label="Temperature range" value="NaN ° - NaN °" />
            </div>
        </div>
    );
}

export default DailyCard;
