import React from 'react'
import { Link } from 'react-router-dom'
import { useState } from 'react'
import { useParams } from 'react-router-dom'
import { useEffect } from 'react'
import { useNavigate} from 'react-router-dom'
import { BASE_URL } from '../assets/baseUrl'
import axios from "axios"
import { TableContainer, Table, TableHead, TableRow, TableCell, TableBody, Paper, IconButton, Modal, Box, TextField, Button, BottomNavigation, BottomNavigationAction, Drawer } from "@mui/material"
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import RestaurantMenuIcon from '@mui/icons-material/RestaurantMenu';
import FavoriteIcon from '@mui/icons-material/Favorite';
import PostAddIcon from '@mui/icons-material/PostAdd';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import ShoppingList from '../Components/ShoppingList'
import MenuBookIcon from '@mui/icons-material/MenuBook';
import IngredientModal from '../Components/IngredientModal'

const AdminUserPage = (props) => {

    const [Recipes, setRecipes] = useState([])
    const navigate = useNavigate()

    // props passed from App.jsx
    const params = useParams()
    const [bearer, setBearer] = props.bearer
    const [userRole, setUserRole] = props.userRole
    const [ingredients, setIngredients] = props.ingredients

    // for BottomNavigation
    const [value, setValue] = useState(0)

    // for editing function
    const [editingRecipe, setEditingRecipe] = useState(null)
    const [openModal, setOpenModal] = useState(false)

    // for Shopping List Drawer
    const [openDrawer, setOpenDrawer] = useState(false)
    const toggleDrawer = (newOpen) => () => {
        setOpenDrawer(newOpen)
    }
    const DrawerList = (
        <Box sx={{ width: 250 }} role="presentation" onClick={toggleDrawer(false)}>
            <ShoppingList bearer={[bearer, setBearer]}/>
        </Box>
    )

    // For fetch and display all recipes
    const handleShowAllRecipes =() => {
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

    // For fetch and display selected recipes
    const handleShowSelectedRecipes =() => {
        const endpoint = BASE_URL + "recipes/selected"

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
    
    // For show ingredient of the recipe 
    const [ingredientModalOpen, setIngredientModalOpen] = useState(false)
    const [recipeId, setRecipeId] = useState(null)
    const handleShowIngredient = (recipeId) => {
        setIngredientModalOpen(true)
        setRecipeId(recipeId)
    }
    const handleCloseIngredientModal = () => {
        setIngredientModalOpen(false);
    }

    // For editing a recipe 
    const handleEditRecipe = (recipeId) => {
        const recipeToEdit = Recipes.find(recipe => recipe.id === recipeId);
        setEditingRecipe(recipeToEdit)
        setOpenModal(true)
    }
    const handleCloseModal = () => {
        setOpenModal(false);
        setEditingRecipe(null);
    }
    const handleSaveChanges = () => {
        const endpoint = BASE_URL + "recipes/update"
        const requestOptions = {
            headers: {
                Authorization: bearer
            }
        }
        axios.put(endpoint, editingRecipe, requestOptions)
            .then(response => {
                console.log("Recipe updated successfully!")
                console.log("recipe name: " + editingRecipe.name)
                const updatedRecipes = Recipes.map(recipe => {
                    if (recipe.id === editingRecipe.id) {
                        return editingRecipe;
                    }
                    return recipe
                })
                setRecipes(updatedRecipes)
                handleCloseModal()
            })
            .catch(error => {
                console.log(error)
            })
    }

    // For deleting a recipe 
    const handleDeleteRecipe = (recipeId) => {
        const endpoint = BASE_URL + 'recipes/' + recipeId
        const requestOptions = {
            headers: {
                Authorization: bearer
            }
        }

        axios.delete(endpoint, requestOptions)
             .then(response => {
                const updatedRecipes = Recipes.filter(recipe => recipe.id !== recipeId);
                setRecipes(updatedRecipes)
             })
             .catch(error => {
                console.log(error.response.data.message)
             })
    }

    // Redirect to add recipe page 
    const handleAddRecipe = () => {
        navigate("/addrecipes")
    }

    useEffect(() => {
        handleShowAllRecipes()
    }, [])

    return(
        <div >
            <h2>Admin User Page</h2>

            <Box sx={{ width: '100%' }}>
            <BottomNavigation
                showLabels
                value={value}
                onChange={(event, newValue) => {
                setValue(newValue);
                }}
            >
                <BottomNavigationAction label="Show All" onClick={handleShowAllRecipes} icon={<RestaurantMenuIcon />} />
                <BottomNavigationAction label="Show Selected" onClick={handleShowSelectedRecipes} icon={<FavoriteIcon />} />
                <BottomNavigationAction label="Add Recipe" onClick={handleAddRecipe} icon={<PostAddIcon />} />
                <BottomNavigationAction label="Shopping List" onClick={toggleDrawer(true)} icon={<ShoppingCartIcon />} />
                <Drawer open={openDrawer} onClose={toggleDrawer(false)} > {DrawerList} </Drawer>
            </BottomNavigation>
            </Box>

            <TableContainer component={Paper} sx={{marginBottom:3}}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell>Name</TableCell>
                        <TableCell>Description</TableCell>
                        <TableCell>Instructions</TableCell>
                        <TableCell>Image</TableCell>
                        <TableCell>Selected</TableCell>
                        <TableCell>Actions</TableCell>
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
                            <TableCell>{recipe.selected ? 'Yes': 'No' }</TableCell>
                            <TableCell>
                                <IconButton onClick={() => handleShowIngredient(recipe.id)}> <MenuBookIcon /> </IconButton>
                                <IconButton onClick={() => handleEditRecipe(recipe.id)}><EditIcon /></IconButton>
                                <IconButton onClick={() => handleDeleteRecipe(recipe.id)}><DeleteIcon /></IconButton>
                            </TableCell>
                        </TableRow>
                        )))}
                </TableBody>
            </Table>
          </TableContainer>      
            <br/><br/>  
          <Link to="/">Go Back Home :D</Link>

          {/* Modal for editing recipe */}
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
                    <h3>Edit Recipe</h3>
                    <TextField
                        label="Name"
                        value={editingRecipe ? editingRecipe.name : ''}
                        onChange={(e) => setEditingRecipe({ ...editingRecipe, name: e.target.value })}
                        fullWidth
                        sx={{ mb: 2 }}
                    />
                    <TextField
                        label="Description"
                        value={editingRecipe ? editingRecipe.description : ''}
                        onChange={(e) => setEditingRecipe({ ...editingRecipe, description: e.target.value })}
                        fullWidth
                        sx={{ mb: 2 }}
                    />
                    <TextField
                        label="Instructions"
                        value={editingRecipe ? editingRecipe.instructions : ''}
                        onChange={(e) => setEditingRecipe({ ...editingRecipe, instructions: e.target.value })}
                        fullWidth
                        multiline
                        rows={4}
                        sx={{ mb: 2 }}
                    />
                    <Button variant="contained" onClick={handleSaveChanges}>Save Changes</Button>
                </Box>
            </Modal>
          
          {/* Modal for ingredients */}
            <IngredientModal
                open={ingredientModalOpen}
                handleClose={handleCloseIngredientModal}
                recipeId={recipeId}
                bearer={bearer}
                ingredients={ingredients}
            />
        </div>
    )
}

export default AdminUserPage