import React from 'react'
import { useState } from 'react'
import { useNavigate} from 'react-router-dom'
import { BASE_URL } from "../assets/baseUrl"
import { Link } from 'react-router-dom'
import axios from "axios"
import { TextField, FormControl, OutlinedInput, InputLabel, InputAdornment, IconButton, Button, Alert, Autocomplete } from '@mui/material';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';

const options = ['Role_Admin', 'Role_User']

const Register = () => {

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [email, setEmail] = useState("")
    const [role, setRole] = useState(options[1])

    const [showPassword, setShowPassword] = React.useState(false);
    const handleClickShowPassword = () => setShowPassword((show) => !show);
    const handleMouseDownPassword = (event) => {event.preventDefault();};

    console.log(username)

    //  For error messages
    const [message, setMessage] = useState("")

    // For navigation
    const navigate = useNavigate()

    // Form submission handler
    const handleSubmit = (e) => {

        e.preventDefault()  
        const endpoint = BASE_URL + "user/register"
        const requestBody = {
                username: username,
                password: password,
                email: email,
                role: role
        }
        // POST Request
        axios.post(endpoint, requestBody)
            .then(response => {
                console.log("Submitted!")
                console.log("Username: " + username)
                console.log("email: " + email)
                console.log("Role: " + role)
                navigate("/login")
            })
            .catch(error => {
                if(error.response.status === 409) {
                    setMessage("User already exists")
                    console.log(error)
                } else {
                    console.log(error)
                }
            })
    }

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <h1>Create Account</h1>
                <TextField sx={{ m: 1, width: '25ch' }} 
                           label="Username" 
                           value={username} 
                           onChange={(e=>setUsername(e.target.value))}/>
                <FormControl sx={{ m: 1, width: '25ch' }} variant="outlined">
                    <InputLabel htmlFor="outlined-adornment-password">Password</InputLabel>
                    <OutlinedInput
                        id="outlined-adornment-password"
                        type={showPassword ? 'text' : 'password'}
                        endAdornment={
                        <InputAdornment position="end">
                            <IconButton
                            aria-label="toggle password visibility"
                            onClick={handleClickShowPassword}
                            onMouseDown={handleMouseDownPassword}
                            edge="end"
                            >
                            {showPassword ? <VisibilityOff /> : <Visibility />}
                            </IconButton>
                        </InputAdornment>
                        }
                        label="Password"
                        value={password} 
                        onChange={(e=>setPassword(e.target.value))}
                    />
                </FormControl>
                <TextField sx={{ m: 1, width: '25ch' }} 
                           label="Email" 
                           value={email} 
                           onChange={(e=>setEmail(e.target.value))}/>   
                <Autocomplete  sx={{ m: 1, width: '25ch'}} disablePortal
                    options={options}
                    value={role}
                    onChange={(e, newValue) => {setRole(newValue);}}
                    renderInput={(params) => <TextField {...params} label="Roles" />}
                    />
                <div><Button type="submit" onClick={handleSubmit} variant="contained">Submit</Button></div>
                {message.length > 0 && <Alert severity='error'>{message}</Alert>}
            </form>
            <p>Have an account? &emsp; <Link to='/login'>Login here</Link></p>
            <p><Link to="/">Back to Home</Link></p>
        </div>
    )
}

export default Register