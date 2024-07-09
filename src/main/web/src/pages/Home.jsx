import React from 'react'
import { Link } from 'react-router-dom'

const Home = () => {

    return (
        <div id="container">
            <div id="center">
                <h1>Welcome To </h1>
                <h1>Taste Trove</h1>
            </div>
            <img id="backgroundimg" src='./background.jpg' alt='background image with pinnapple' width='100%' height='100%'></img>
            <Link to="/login">Please Login</Link>
        </div>
    )
}

export default Home