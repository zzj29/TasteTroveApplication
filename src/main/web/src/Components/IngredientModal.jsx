import React from 'react'
import { useEffect } from 'react'
import { useState } from 'react'
import axios from 'axios';
import { Modal, Box, List, ListItem, ListItemText, ListItemIcon, Checkbox, Button } from '@mui/material';
import { BASE_URL } from '../assets/baseUrl';

const IngredientModal = ({ open, handleClose, recipeId, bearer, ingredients }) => {
    const [selectedIngredients, setSelectedIngredients] = useState([]);

    console.log(recipeId)
    console.log(bearer)
    console.log(ingredients)

     // show ingredient of recipe based on recipeId
    const getSelectedIngredient = () => {
        const endpoint = BASE_URL + 'recipes/' + recipeId + '/ingredients'
        const requestOptions = {
            headers: {
                Authorization: bearer
            }
        }
        axios.get(endpoint, requestOptions)
             .then(response => {
                setSelectedIngredients(response.data)
                console.log(selectedIngredients)
             })
             .catch(error=> {
                console.log(error.response.data.message)
            })
    }
    useEffect(() => {
        getSelectedIngredient();
    }, [open]);

    // Change selection of linked ingredients
    const handleCheckboxChange = (ingredientId) => {
        if (selectedIngredients.includes(ingredientId)) {
            setSelectedIngredients(selectedIngredients.filter((id) => id !== ingredientId));
        } else {
            setSelectedIngredients([...selectedIngredients, ingredientId]);
        }
    }

    // Link the selected ingredients to the recipe
    const handleAddIngredients = () => {
        const endpoint = BASE_URL + `recipes/${recipeId}/linkingredient`
        const requestOptions = {
            headers: {
                Authorization: bearer,
            }
        }
        axios.post(endpoint, selectedIngredients, requestOptions)
            .then((response) => {
                console.log('Ingredients linked to recipe successfully!')
                handleClose()
            })
            .catch((error) => {
                console.log(error)
            })
    }

    return (
        <Modal open={open} onClose={handleClose}>
            <Box
                sx={{
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                width: 400,
                bgcolor: 'background.paper',
                border: '2px solid #000',
                boxShadow: 24,
                p: 4,
                }}
            >
                <h3>Show Ingredients</h3>
                <List>
                {selectedIngredients.map((ingredient) => (
                    <ListItem key={ingredient.id} disablePadding>
                    <ListItemIcon>
                        <Checkbox
                        checked={selectedIngredients.includes(ingredient.id)}
                        onChange={() => handleCheckboxChange(ingredient.id)}
                        />
                    </ListItemIcon>
                    <ListItemText primary={ingredient.name} />
                    </ListItem>
                ))}
                </List>
                {/* <Button variant="contained" onClick={handleAddIngredients}>
                Add Ingredients
                </Button> */}
            </Box>
        </Modal>
    )
}

export default IngredientModal