# ☀️ Solar Forecast — Real-time Weather & Solar Insights

Solar Forecast is a lightweight web application for monitoring weather forecasts and solar energy potential across the globe. Built with Spring Boot for the backend and ReactJS for the frontend, it fetches live data from the Open-Meteo API and presents it in an interactive and visually intuitive interface.

## 📌 Project Overview:
Solar Forecast allows users to view 7-day weather forecasts for any selected location on the map. The page also includes a weekly summary with key weather indicators and estimated solar energy potential, calculated using a custom formula based on available forecast data.

Key features include:

✅ Real-time weather data from the [Open-Meteo API](https://open-meteo.com/)  
✅ Interactive world map with location selection (powered by Leaflet)  
✅ 7-day forecast with dynamic weather icons and temperature visualization  
✅ Weekly summary including average atmospheric pressure, sun exposure duration, temperature range, and a general weather condition overview  
✅ Fast and lightweight single-page application (SPA)  
✅ Built entirely as a frontend-backend integration, Solar Forecast is a concise yet functional example of real-time weather visualization  

**🛠️ Technologies Used:**

- **Backend:** Java + Spring Boot
- **Frontend:** ReactJS + Leaflet.js
- **API Source:** [Open-Meteo API](https://open-meteo.com/)


## 🚀 How to Run the Application?

## 1️⃣ Clone the Repository
```bash
git clone https://github.com/your-username/solar-forecast  
cd solar-forecast
```

## 2️⃣ Run the backend server
```bash
cd backend
./gradlew bootRun
```

## 3️⃣ Run the frontend
```bash
cd frontend
npm install
npm start
```

## 🌐 Deployment
You can try the live version of the project here:  
🔗 https://solar-forecast-4tk9.onrender.com
