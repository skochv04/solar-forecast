import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HelloPage from './pages/HelloPage/HelloPage'
import ForecastPage from "./pages/ForecastPage/ForecastPage";
import 'leaflet/dist/leaflet.css';

function App() {
  return (
      <Router>
        <Routes>
          <Route path="/hello" element={<HelloPage />} />
            <Route path="/forecast" element={<ForecastPage />} />
        </Routes>
      </Router>
  );
}

export default App;
