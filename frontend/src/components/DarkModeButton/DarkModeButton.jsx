import React, { useState } from 'react';
import './DarkModeButton.css';
import lightMode from '../../assets/icons/lightMode.svg';
import darkMode from '../../assets/icons/darkMode.svg';

function DarkModeButton() {
    const [isDarkMode, setIsDarkMode] = useState(false);

    const toggleMode = () => {
        setIsDarkMode(prev => {
            const newMode = !prev;
            document.documentElement.setAttribute('data-theme', newMode ? 'dark' : 'light');
            return newMode;
        });
    };

    const icon = isDarkMode ? lightMode : darkMode;

    return (
        <button className="dark-mode-button" onClick={toggleMode} aria-label="Toggle dark mode">
            <img key={icon} src={icon} alt="" className="dark-mode-icon"/>
        </button>
    );
}

export default DarkModeButton;