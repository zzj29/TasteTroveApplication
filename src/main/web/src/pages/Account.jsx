import React from "react";
import axios from 'axios';
import { BASE_URL } from '../assets/baseUrl';
import { TextField, Button, Alert, Autocomplete } from '@mui/material';
import { useEffect } from "react";
import { useState } from 'react'

const options = ['Role_Admin', 'Role_User']

const Account = (props) => {

    const [bearer, setBearer] = props.bearer

    const [user, setUser] = useState(null);
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [userRole, setUserRole] = props.userRole

    const [message, setMessage] = useState("")

    const getUser = () => {
        const endpoint = BASE_URL + "user/me"

        const requestOptions = {
            headers: {
                Authorization: bearer
            }
        }

        axios.get(endpoint, requestOptions)
             .then(response => {
                setUser(response.data)
                setUsername(response.data.username)
                setEmail(response.data.email)
                setPassword(response.data.password)
             })
             .catch(error => {
                if (error.response.status === 401) {
                    setMessage("Please login first")
                    console.log(error)
                }
                console.log(error)
             })
    }

    const handleUpdate = (e) => {
        e.preventDefault()

        const endpoint = BASE_URL + "user/update"

        const requestBody = {
            id: user.id,
            username: username,
            email: email,
            password: password,
            role: userRole
        }

        const requestOptions = {
            headers: {
                Authorization: bearer
            }
        }

        axios.put(endpoint, requestBody, requestOptions)
             .then(response => {
                console.log("Updated!")
                console.log("Username: " + username)
             })
             .catch(error => {
                console.log(error)
             })
    }

useEffect(() => {
    getUser()
}, [])

    return (
        <div>
            <h2>Account Information</h2>
            {message !== "" && <Alert severity="error">{message}</Alert>}
            <TextField
                label="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                fullWidth
                sx={{ mb: 2 }}
            />
            <TextField
                label="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                fullWidth
                sx={{ mb: 2 }}
            />
            {/* <TextField
                type="password"
                label="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                fullWidth
                sx={{ mb: 2 }}
            /> */}
            <Autocomplete  sx={{ m: 1, width: '25ch'}} disablePortal
                    options={options}
                    value={userRole}
                    onChange={(e, newValue) => {setUserRole(newValue);}}
                    renderInput={(params) => <TextField {...params} label="Role" />}
                    />
            <Button variant="contained" onClick={handleUpdate}>
                Update Information
            </Button>
        </div>
    )
}

export default Account