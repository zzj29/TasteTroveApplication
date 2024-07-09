import React from 'react'
import axios from "axios"
import { BASE_URL } from "../assets/baseUrl"
import { Link } from 'react-router-dom'
import { useEffect } from 'react'
import { useState } from 'react'
import { TableContainer, Table, TableHead, TableRow, TableCell, TableBody, Paper, IconButton, Modal, Box, TextField, Button, Autocomplete } from "@mui/material"
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

const Ingredients = (props) => {

    // props passed from App.jsx
    const [bearer, setBearer] = props.bearer
    const [userRole, setUserRole] = props.userRole
    const [ingredients, setIngredients] = props.ingredients

    // For add new ingredient
    const [newIngredientName, setNewIngredientName] = useState("");
    const [newIngredientType, setNewIngredientType] = useState("");

    //  For error messages
    const [message, setMessage] = useState("")

    // For edit ingredient
    const [editingIngredient, setEditingIngredient] = useState(null);

    const [openModal, setOpenModal] = useState(false);

    const types = ['Meat', 'Seafood', 'Vegetable', 'Main']

    const getIngredients = () => {
        const endpoint = BASE_URL + "ingredients"

        const requestOptions = {
            headers:{
                Authorization: bearer
            }
        }

        axios.get(endpoint, requestOptions)
             .then(respose => {
                setIngredients(respose.data)
                console.log(bearer)
             })
             .catch(error => {
                console.log(error)
             })
    }

    useEffect(()=>{
        // Run GET request in here
        getIngredients()
    }, [ingredients])

    const handleEdit = (ingredientId) => {
        const ingredientToEdit = ingredients.find(ingredient => ingredient.id === ingredientId);
        setEditingIngredient(ingredientToEdit)
        setOpenModal(true)
    }

    const handleCloseModal = () => {
        setOpenModal(false);
        setEditingIngredient(null);
    }

    const handleSaveChanges = () => {
        
        const endpoint = BASE_URL + "ingredients/update"

        const requestOptions = {
            headers: {
                Authorization: bearer
            }
        }

        axios.put(endpoint, editingIngredient, requestOptions)
            .then(response => {
                console.log("Ingredient updated successfully!")
                console.log("ingredient name: " + editingIngredient.name)
                const updatedIngredients = ingredients.map(ingredient => {
                    if (ingredient.id === editingIngredient.id) {
                        return editingIngredient;
                    }
                    return ingredient
                })
                setIngredients(updatedIngredients)
                handleCloseModal()
            })
            .catch(error => {
                console.log(error)
            })
    }

    const handleDelete = (ingredientId) => {
        const endpoint = BASE_URL + 'ingredients/' + ingredientId
        const requestOptions = {
            headers: {
                Authorization: bearer
            }
        }

        axios.delete(endpoint, requestOptions)
             .then(response => {
                const updatedIngredients = ingredients.filter(ingredient => ingredient.id !== ingredientId);
                setIngredients(updatedIngredients)
             })
             .catch(error => {
                if (error.response.status === 409) {
                    setMessage("Ingredient is is used in recipes. Please delete the recipe first.")
                    console.log(error)
                } else {
                    console.log(error.response.data.message)
                }
             })
    }

    const handleAdd = () => {
        const newIngredient = {
            name: newIngredientName,
            type: newIngredientType
        };
        const endpoint = BASE_URL + 'ingredients/register'
        const requestOptions = {
            headers: {
                Authorization: bearer
            }
        }
        axios.post(endpoint, newIngredient, requestOptions)
             .then(response => {
                // Add the new ingredient to the list
                const updatedIngredients = [...ingredients, response.data]
                setIngredients(updatedIngredients)
                // Clear the input fields
                setNewIngredientName("")
                setNewIngredientType("")
             })
             .catch(error => {
                if(error.response.status === 500) {
                    setMessage("Cannot add the same ingredient.")
                    console.log(error)
                }
                console.log(error)
             })
    }

    return (
        <div>
            <h2>Ingredients</h2>
            <TableContainer component={Paper} sx={{marginBottom:3}}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>Name</TableCell>
                            <TableCell>Type</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {ingredients.map((ingredient => (
                            <TableRow key={ingredient.id}>
                                <TableCell>{ingredient.id}</TableCell>
                                <TableCell>{ingredient.name}</TableCell>
                                <TableCell>{ingredient.type}</TableCell>
                                <TableCell>
                                <IconButton onClick={() => handleEdit(ingredient.id)}><EditIcon /></IconButton>
                                <IconButton onClick={() => handleDelete(ingredient.id)}><DeleteIcon /></IconButton>
                            </TableCell>
                            </TableRow>
                        )))}

                    </TableBody>
                </Table>
            </TableContainer>
            <h2>Add New Ingredient</h2>
            <TextField
                label="Name"
                value={newIngredientName}
                onChange={(e) => setNewIngredientName(e.target.value)}
                fullWidth
                sx={{ mb: 2 }}
            />
            {/* <TextField
                label="Type"
                value={newIngredientType}
                onChange={(e) => setNewIngredientType(e.target.value)}
                fullWidth
                sx={{ mb: 2 }}
            /> */}
            <Autocomplete
                disablePortal
                options={types}
                value={newIngredientType}
                itemType='string'
                onChange={(e, newValue) => setNewIngredientType(newValue)}
                sx={{ width: 200 }}
                renderInput={(params) => <TextField {...params} label="Type" />}
            />
            <br/>
            <Button variant="contained" onClick={handleAdd}>Add Ingredient</Button>
            <br/><br/>
            <Link to="/">Go Back Home :D</Link>

          {/* Modal for editing ingredient */}
          <Modal
                open={openModal}
                onClose={() => setOpenModal(false)}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={{
                    position: 'absolute',
                    top: '50%',
                    left: '50%',
                    transform: 'translate(-50%, -50%)',
                    width: 400,
                    bgcolor: 'background.paper',
                    border: '2px solid #000',
                    boxShadow: 24,
                    p: 4,
                }}>
                    <h3>Edit Ingredient</h3>
                    <TextField
                        label="Name"
                        value={editingIngredient ? editingIngredient.name : ''}
                        onChange={(e) => setEditingIngredient({ ...editingIngredient, name: e.target.value })}
                        fullWidth
                        sx={{ mb: 2 }}
                    />
                    <TextField
                        label="Type"
                        value={editingIngredient ? editingIngredient.type : ''}
                        onChange={(e) => setEditingIngredient({ ...editingIngredient, type: e.target.value })}
                        fullWidth
                        sx={{ mb: 2 }}
                    />
                    {/* <Autocomplete
                        disablePortal
                        options={types}
                        value={editingIngredient ? editingIngredient.type : ''}
                        onChange={(e) => setEditingIngredient({ ...editingIngredient, type: e.target.value })}
                        sx={{ width: 200 }}
                        renderInput={(params) => <TextField {...params} label="Type" />}
                    /> */}
                    <Button variant="contained" onClick={handleSaveChanges}>Save Changes</Button>
                </Box>
            </Modal>

            {/* Modal for error message */}
            <Modal
                open={message !== ""}
                onClose={() => setMessage("")}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={{
                    position: 'absolute',
                    top: '50%',
                    left: '50%',
                    transform: 'translate(-50%, -50%)',
                    width: 400,
                    bgcolor: 'background.paper',
                    border: '2px solid #000',
                    boxShadow: 24,
                    p: 4,
                }}>
                    <h3>Error</h3>
                    <p>{message}</p>
                </Box>
            </Modal>

        </div>
    )
}

export default Ingredients