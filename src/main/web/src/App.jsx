import { useState } from 'react'
import { Routes } from 'react-router-dom'
import { Route } from 'react-router-dom'
import './App.css'
import Login from './pages/Login'
import Home from './pages/Home'
import About from './pages/About'
import ResponsiveAppBar from './Components/NavBar'
import Footer from './Components/Footer'
import Register from './pages/Register'
import AdminUserPage from './pages/AdminUserPage'
import UserPage from './pages/UserPage'
import Logout from './pages/Logout'
import Ingredients from './pages/Ingredients'
import Account from './pages/Account'
import AddRecipes from './pages/AddRecipe'

function App() {

  const [bearer, setBearer] = useState("")
  const [userRole, setUserRole] = useState("")
  const [ingredients, setIngredients] = useState([])

  return (
    <>
      <ResponsiveAppBar userRole={[userRole, setUserRole]}/>
      <Routes>
        <Route path="/" element={<Home/>} />
        <Route path="/about" element={<About/>}/>
        <Route path="/login" element={<Login bearer={[bearer, setBearer]} userRole={[userRole, setUserRole]}/>}/>
        <Route path="/register" element={<Register/>}/>
        <Route path="/admin-page" element={<AdminUserPage bearer={[bearer, setBearer]} userRole={[userRole, setUserRole]} ingredients={[ingredients, setIngredients]}/>}/>
        <Route path="/ingredients" element={<Ingredients bearer={[bearer, setBearer]} userRole={[userRole, setUserRole]} ingredients={[ingredients, setIngredients]}/>}/>
        <Route path="/addrecipes" element={<AddRecipes bearer={[bearer, setBearer]} userRole={[userRole, setUserRole]} ingredients={[ingredients, setIngredients]}/>}/>
        <Route path="/user-page" element={<UserPage bearer={[bearer, setBearer]} userRole={[userRole, setUserRole]}/>}/>
        <Route path="/account" element={<Account bearer={[bearer, setBearer]} userRole={[userRole, setUserRole]}/>}/>
        <Route path="/logout" element={<Logout bearer={[bearer, setBearer]}/>}/>
      </Routes>
      <Footer />
    </>
  )
}

export default App
