import React, { useState } from 'react';
import { MapContainer, TileLayer, Marker, useMapEvents } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import './ForecastPage.css';
import DarkModeButton from "../../components/DarkModeButton/DarkModeButton";
import WeeklySummaryCard from "../../components/WeeklySummaryCard/WeeklySummaryCard";
import L from 'leaflet';
import DailyCard from "../../components/DailyCard/DailyCard";

const defaultCenter = [50.06, 19.95]; // Cracow

function LocationMarker({ onClickMap }) {
    useMapEvents({
        click(e) {
            const { lat, lng } = e.latlng;
            onClickMap(lat, lng);
        },
    });

    return null;
}

export default function ForecastPage() {
    const [forecast, setForecast] = useState(null);
    const [dailyForecast, setDailyForecast] = useState(null);
    const [coords, setCoords] = useState(null);
    const fetchForecast = (lat, lon) => {
        const url = `${process.env.REACT_APP_API_URL}api/forecast/weekly/summary?latitude=${lat}&longitude=${lon}`;
        fetch(url)
            .then((res) => res.json())
            .then((data) => {
                setForecast(data);
                setCoords({ lat, lon });
            })
            .catch((err) => {
                console.error('Fetch error:', err);
                setForecast({ error: 'Error connecting to backend' });
            });
        fetchDailyForecast(lat, lon);
    };

    const fetchDailyForecast = (lat, lon) => {
        const url = `${process.env.REACT_APP_API_URL}api/forecast/weekly?latitude=${lat}&longitude=${lon}`;
        fetch(url)
            .then((res) => res.json())
            .then((data) => {
                console.log("Daily forecast data:", data);
                setDailyForecast(data);
            })
            .catch((err) => {
                console.error('Fetch daily error:', err);
                setDailyForecast(null);
            });
    };

    function getDayName(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString('en-US', { weekday: 'long' });
    }

    return (
        <div className="container">
            <div className="dark-mode-container">
                <DarkModeButton></DarkModeButton>
            </div>
            <div className="content">
                <div className="left-block">
                    <div className="map-block">
                    <h1>Click on the map to get forecast</h1>

                    <MapContainer center={defaultCenter} zoom={6} style={{ height: '360px', width: '100%' }}>
                        <TileLayer
                            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                            url="https://tile.openstreetmap.org/{z}/{x}/{y}.png"
                        />
                        <LocationMarker onClickMap={fetchForecast} />
                        {coords && (
                            <Marker position={[coords.lat, coords.lon]} icon={L.icon({ iconUrl: "https://unpkg.com/leaflet@1.9.3/dist/images/marker-icon.png", iconSize: [25, 41], iconAnchor: [12, 41] })} />
                        )}
                    </MapContainer>
                    </div>

                    <div className="summary-block">
                        <h1>Weekly weather summary</h1>
                        {forecast ? (
                            !forecast.error ? (
                                <WeeklySummaryCard
                                    minTemp={forecast.minTemperaturePerWeek}
                                    maxTemp={forecast.maxTemperaturePerWeek}
                                    pressure={forecast.averagePressurePerWeek}
                                    sunExposure={forecast.averageSunExposurePerWeek}
                                    summary={forecast.weatherSummary}
                                />
                            ) : (
                                <div className="error-message">
                                    An error occurred: {forecast.error}
                                </div>
                            )
                        ) : (
                            <p>No data yet</p>
                        )}
                    </div>
                </div>
                <div className="right-block">
                    <h1>Forecast result:</h1>
                    <div className="daily-cards">
                        {dailyForecast && dailyForecast.dailyForecasts && dailyForecast.dailyForecasts.length > 0 ? (
                            dailyForecast.dailyForecasts.map((day, idx) => (
                                <DailyCard
                                    key={idx}
                                    date={day.date}
                                    dayName={getDayName(day.date)}
                                    energy={day.estimatedEnergy}
                                    minTemp={day.minTemperaturePerDay}
                                    maxTemp={day.maxTemperaturePerDay}
                                    weatherCode={day.weatherCode}
                                />
                            ))
                        ) : (
                            <p>No data yet</p>
                        )}
                    </div>

                </div>
            </div>
        </div>
    );
}
