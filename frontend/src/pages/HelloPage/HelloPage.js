import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';

export default function HelloPage() {
    const navigate = useNavigate();
    const [message, setMessage] = useState('');

    useEffect(() => {
        fetch(`${process.env.REACT_APP_API_URL}api/hello`)
            .then((res) => res.text())
            .then((data) => {
                setMessage(data);
            })
            .catch((err) => {
                console.error('Fetch error:', err);
                setMessage('Error connecting to backend');
            });
    }, []);


    return (
        <div className="container">
            <h2>{message}</h2>
        </div>
    );
}
