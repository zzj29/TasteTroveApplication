import React from 'react'
import axios from "axios"
import { useState } from 'react'
import { Link } from 'react-router-dom'
import { useParams } from 'react-router-dom'
import { BASE_URL } from '../assets/baseUrl'
import { useEffect } from 'react'
import { TableContainer, Table, TableHead, TableRow, TableCell, TableBody, Paper } from "@mui/material"
import Checkbox from '@mui/material/Checkbox';
import FavoriteBorder from '@mui/icons-material/FavoriteBorder';
import Favorite from '@mui/icons-material/Favorite';

const UserPage = (props) => {

    const params = useParams()

    const [bearer, setBearer] = props.bearer
    const [userRole, setUserRole] = props.userRole

    const [Recipes, setRecipes] = useState([])

    const getRecipes =() => {
        const endpoint = BASE_URL + "recipes"

        const requestOptions = {
            headers: {
                Authorization: bearer
            }
        }

        axios.get(endpoint, requestOptions)
            .then(response => {
                setRecipes(response.data)
                console.log(bearer)
            })
            .catch(error=> {
                console.log(error.response.data.message)
            })
    }

    const handleSelectChange = (recipe, selected) => {
        const endpoint = BASE_URL + 'recipes/update'
        
        const requestOptions = {
            headers: {
                Authorization: bearer
            }
        }

        recipe.selected = !selected;

        axios.put(endpoint, recipe, requestOptions)
             .catch(error => {
                console.log(error)
             })
    }

    useEffect(() => {
        getRecipes()
    },[Recipes])

    return (
        <div>
          <h2>User Page</h2>
          <TableContainer component={Paper} sx={{marginBottom:3}}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell>Name</TableCell>
                        <TableCell>Description</TableCell>
                        <TableCell>Instructions</TableCell>
                        <TableCell>Image</TableCell>
                        <TableCell>Select</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {Recipes.map((recipe => (
                        <TableRow key={recipe.id}>
                            <TableCell>{recipe.id}</TableCell>
                            <TableCell>{recipe.name}</TableCell>
                            <TableCell>{recipe.description}</TableCell>
                            <TableCell>{recipe.instructions}</TableCell>
                            <TableCell><img src={recipe.image} alt={recipe.name} style={{ width: '300px', height: '300px' }} /></TableCell>
                            <TableCell>
                                <Checkbox icon={<FavoriteBorder />} checkedIcon={<Favorite />} checked={recipe.selected} onChange={() => handleSelectChange(recipe, recipe.selected)}/>    
                            </TableCell>
                        </TableRow>
                        )))}
                </TableBody>
            </Table>
          </TableContainer>
          <Link to="/">Back to home</Link>
        </div>
      )
}

export default UserPage