import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HelloPage from './pages/HelloPage/HelloPage'

function App() {
  return (
      <Router>
        <Routes>
          <Route path="/hello" element={<HelloPage />} />
        </Routes>
      </Router>
  );
}

export default App;
