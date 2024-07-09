import React from "react";
import { Link } from "react-router-dom";

const About = (props) => {

    const propsText = props.words
    const propsMethod = props.method

    return (
        <div id="container">
            <div id="center">
            	<h1>About</h1>
            	<p>TasteTrove is a web application designed to streamline meal planning and grocery shopping for families. Users can register as admin or family members, with admin users having the ability to add, update, and delete recipes. Family members can browse recipes, mark their preferences, and request dishes for upcoming meals.</p>
            </div>
            <img id="backgroundimg" src='./background.jpg' alt='background image with pinnapple' width='100%' height='100%'></img>
            <Link to="/">Go Back To Home Page</Link>
        </div>
    )
}

export default About