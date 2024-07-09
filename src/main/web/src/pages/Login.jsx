import React from 'react'
import { useNavigate} from 'react-router-dom'
import { useState } from 'react'
import { Link } from 'react-router-dom'
import { TextField, FormControl, OutlinedInput, InputLabel, InputAdornment, IconButton, Button, Alert } from '@mui/material';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import axios from "axios"
import { BASE_URL } from "../assets/baseUrl"

const Login = (props) => {

    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")

    //  For error messages
    const [message, setMessage] = useState("")
    
    // For navigation
    const navigate = useNavigate()

    const [bearer, setBearer] = props.bearer
    const [userRole, setUserRole] = props.userRole

    const [showPassword, setShowPassword] = React.useState(false);
    const handleClickShowPassword = () => setShowPassword((show) => !show);
    const handleMouseDownPassword = (event) => {event.preventDefault();};

    console.log(username)

    const handleSubmit = (e) => {
        e.preventDefault()
        const endpoint = BASE_URL + "auth/login"
        // Request body is an object that you're posting. Make sure the key names here match attribute names in the model class (backend)
        const requestOptions = {
            auth: {
                username: username,
                password: password
            }
        }
        // POST Request
        axios.post(endpoint, {}, requestOptions)
             .then(response => {
                console.log("Login Success!")
                console.log("Username: " + username)
                setBearer("Bearer " + response.data)
                console.log("Bearer "+response.data)
                // Fectch user's role after successful login
                axios.get(BASE_URL + 'user/me', {
                    headers: {
                        Authorization: 'Bearer ' + response.data
                    }
                })
                    .then(roleResponse => {
                        const role = roleResponse.data.role
                        setUserRole(role)
                        if (role === "Role_Admin") {navigate("/admin-page")}
                        else if (role === "Role_User") {navigate("/user-page")}
                        else {
                            setMessage("Invalid user role")
                            console.log(error)
                        }
                    })
                    .catch(error => {
                        console.log(error)
                    })
            })
             .catch(error => {
                // Can target specific HTTP error codes and display appropriate messages
                if (error.response.status === 401) {
                  setMessage("Please provide valid username and password")
                  // This will print entire error object out to console. You can see what fields to access from here
                  console.log(error)
                }
                else {
                  console.log(error.response.data.message)
                }
              })
    }

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <h1>Login</h1>
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
                <div><Button type="submit" onClick={handleSubmit} variant="contained">Submit</Button></div>
                {message.length > 0 && <Alert severity='error'>{message}</Alert>}
            </form>
            <div>
                <p>Don't have an account? &emsp; <Link to='/register'>Register here</Link></p>
                <p><Link to="/">Back to Home</Link></p>
            </div>
        </div>
    )
}

export default Login