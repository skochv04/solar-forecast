import React, { useState } from 'react';
import { MapContainer, TileLayer, Marker, useMapEvents } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import L from 'leaflet';

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
    const [coords, setCoords] = useState(null);
    const fetchForecast = (lat, lon) => {
        const url = `${process.env.REACT_APP_API_URL}api/forecast/weekly?latitude=${lat}&longitude=${lon}`;
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
    };

    return (
        <div className="container">
            <h2>Click on the map to get forecast</h2>

            <MapContainer center={defaultCenter} zoom={6} style={{ height: '300px', width: '100%' }}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                <LocationMarker onClickMap={fetchForecast} />
                {coords && (
                    <Marker position={[coords.lat, coords.lon]} icon={L.icon({ iconUrl: "https://unpkg.com/leaflet@1.9.3/dist/images/marker-icon.png", iconSize: [25, 41], iconAnchor: [12, 41] })} />
                )}
            </MapContainer>

            <div style={{ marginTop: '20px' }}>
                <h3>Forecast result:</h3>
                <pre>{forecast ? JSON.stringify(forecast, null, 2) : 'No data yet'}</pre>
            </div>
        </div>
    );
}
