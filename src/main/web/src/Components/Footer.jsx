import React from "react";
import { Link } from "react-router-dom";
import Divider from '@mui/material/Divider';

const Footer = () => {

    return (
        <div id="footer">
            <Divider />
            <p>@ Taste Trove 2024 &emsp; &emsp; <Link to="/about">About Us</Link> </p>
        </div>
    )
}

export default Footer