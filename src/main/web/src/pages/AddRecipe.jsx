import React from 'react'
import { Link } from 'react-router-dom'
import { useState } from 'react'
import { useEffect } from 'react'
import { useNavigate} from 'react-router-dom'
import { BASE_URL } from '../assets/baseUrl'
import axios from "axios"
import { TextField, Button } from "@mui/material"
import Box from '@mui/material/Box';
import FormLabel from '@mui/material/FormLabel';
import FormControl from '@mui/material/FormControl';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormHelperText from '@mui/material/FormHelperText';
import Checkbox from '@mui/material/Checkbox';

const AddRecipes = (props) => {

    // props passed from App.jsx
    const [bearer, setBearer] = props.bearer
    const [userRole, setUserRole] = props.userRole
    const [ingredients, setIngredients] = props.ingredients

    // For add new recipe
    const [newRecipeName, setnewRecipeName] = useState("");
    const [newRecipeDescrip, setnewRecipeDescrip] = useState("");
    const [newRecipeInstruction, setnewRecipeInstruction] = useState("");
    const [newRecipeImage, setnewRecipeImage] = useState("");

    // For selected ingredients of the recipe 
    const [selectedIngredients, setSelectedIngredients] = useState([]);

    const navigate = useNavigate()

    const handleCheckboxChange = (ingredientId) => {
        const updatedSelectedIngredients = [...selectedIngredients]
        if (updatedSelectedIngredients.includes(ingredientId)) {
            updatedSelectedIngredients.splice(updatedSelectedIngredients.indexOf(ingredientId), 1)
        } else {
            updatedSelectedIngredients.push(ingredientId)
        }
        setSelectedIngredients(updatedSelectedIngredients)
    }

    const handleAdd = () => {
        // Add new recipe
        const newRecipe = {
            name: newRecipeName,
            description: newRecipeDescrip,
            instructions: newRecipeInstruction,
        };
        const endpoint = BASE_URL + 'recipes/register'
        const requestOptions = {
            headers: {
                Authorization: bearer
            }
        }
        axios.post(endpoint, newRecipe, requestOptions)
             .then(response => {
                // Link the selected ingredients to the new recipe
                const recipeId = response.data.id;
                const linkIngredientEndpoint = BASE_URL + `recipes/${recipeId}/linkingredient`;
                console.log(selectedIngredients)
                console.log(linkIngredientEndpoint)
                axios.post(linkIngredientEndpoint, selectedIngredients, requestOptions)
                     .then(linkIngredientResponse => {
                        // Redirect to the table of recipes 
                        navigate("/admin-page")
                     })
                     .catch(linkIngredientError => {
                        console.log(linkIngredientError);
                    })
             })
             .catch(error => {
                console.log(error)
             })
    }

    return (
        <div>
            <h2>Add New Recipe</h2>
            <TextField
                label="Name"
                value={newRecipeName}
                onChange={(e) => setnewRecipeName(e.target.value)}
                fullWidth
                sx={{ mb: 2 }}/>
            <TextField
                label="Description"
                value={newRecipeDescrip}
                onChange={(e) => setnewRecipeDescrip(e.target.value)}
                fullWidth
                sx={{ mb: 2 }}/>
            <TextField
                label="Instructions"
                value={newRecipeInstruction}
                onChange={(e) => setnewRecipeInstruction(e.target.value)}
                fullWidth
                multiline
                rows={4}
                sx={{ mb: 2 }}/>
            <FormControl sx={{ m: 3 }} component="fieldset" variant="standard">
                <FormLabel component="legend">Select Ingredients:</FormLabel>
                <FormGroup>
                    {ingredients.map(ingredient => (
                        <FormControlLabel
                            key={ingredient.id}
                            control={<Checkbox checked={selectedIngredients.includes(ingredient.id)} onChange={() => handleCheckboxChange(ingredient.id)} />}
                            label={ingredient.name}
                        />
                    ))}
                </FormGroup>
            </FormControl>
            <br/>
            <Button variant="contained" onClick={handleAdd}>Add Recipe</Button>
        </div>
    )
}

export default AddRecipes