import React from 'react';
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { Button } from '@mui/material';

const Logout = (props) => {
    const [bearer, setBearer] = props.bearer
    const navigate = useNavigate()

    const handleLogout = () => {
        setBearer("");
        // Redirect to the login page 
        navigate("/login")
    };

    return (
        <div>
            <h2>Logout Page</h2>
            <Button variant="contained" onClick={handleLogout}>Logout</Button>
        </div>
    );
};

export default Logout;
