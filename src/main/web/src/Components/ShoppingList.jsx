import React, { useState, useEffect } from 'react';
import {Box, TableContainer, Table, TableHead, TableRow, TableCell, TableBody, Paper } from '@mui/material';
import axios from 'axios';
import { BASE_URL } from '../assets/baseUrl';

const ShoppingList = (props) => {

  const [ingredients, setIngredients] = useState([]);
  const [bearer, setBearer] = props.bearer

    const getIngredientsForSelectedRecipes = () => {
        const endpoint = BASE_URL + "recipes/selected/ingredients"

        const requestOptions = {
            headers: {
                Authorization: bearer
            }
        }

        axios.get(endpoint, requestOptions)
            .then(response => {
                setIngredients(response.data)
                console.log(bearer)
            })
            .catch(error=> {
                console.log(error.response.data.message)
            })
    }

  useEffect(() => {
    getIngredientsForSelectedRecipes()
  }, []);

  return (
    <Box sx={{ width: '90%', position: 'absolute', left: '5%'}}>
        <h3>Shopping List - Items</h3>
        <TableContainer component={Paper} sx={{marginBottom:3}}>
          <Table>
            {/* <TableHead>
              <TableRow>
                <TableCell>Items</TableCell>
                <TableCell>Quantity</TableCell>
              </TableRow>
            </TableHead> */}
            <TableBody>
              {ingredients.map((ingredient, index) => (
                <TableRow key={index}>
                  <TableCell>{ingredient.name}</TableCell>
                  {/* <TableCell>{ingredient.quantity}</TableCell> */}
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
    </Box>
  );
};

export default ShoppingList;
