import * as React from 'react';
import { AppBar, Box, Toolbar, IconButton, Typography, Menu, Container, Button, Tooltip, MenuItem, Alert } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import AccountCircle from '@mui/icons-material/AccountCircle';
import CatchingPokemonIcon from '@mui/icons-material/CatchingPokemon';
import { Link } from 'react-router-dom'
import { useNavigate} from 'react-router-dom'
import { useState } from 'react'

function ResponsiveAppBar(props) {

  const [userRole, setUserRole] = props.userRole
  const navigate = useNavigate()
  const [message, setMessage] = useState("")

  const [anchorElNav, setAnchorElNav] = React.useState(null)
  const [anchorElUser, setAnchorElUser] = React.useState(null)

  const handleOpenNavMenu = (event) => {setAnchorElNav(event.currentTarget)}
  const handleOpenUserMenu = (event) => {setAnchorElUser(event.currentTarget)}

  const handleCloseNavMenu = () => {setAnchorElNav(null)}
  const handleCloseUserMenu = () => {setAnchorElUser(null)}
  const handleAlertClose = () => {setMessage("")}

  const handleClickRecipesButton = () => {
    if (userRole === "Role_Admin") {navigate("/admin-page")}
    else if (userRole === "Role_User") {navigate("/user-page")}
    else {navigate("/login")}
  }

  const handleClickIngredientButton = () => {
    if (userRole === "Role_Admin") {navigate("/ingredients")}
    else {setMessage("Please login as an admin user")}
  }

  return (
    <AppBar position="sticky" sx={{position: 'fixed', top : 0 }} >
      <Container maxWidth="xl">
        <Toolbar disableGutters>

          <CatchingPokemonIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }} />
          <Typography variant="h6" noWrap component={Link} to="/"
            sx={{ mr: 2, display: { xs: 'none', md: 'flex' }, fontWeight: 700, color: 'inherit', textDecoration: 'none',}}>
            TasteTrove </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
            <IconButton size="large" aria-label="account of current user" aria-controls="menu-appbar" aria-haspopup="true"
              onClick={handleOpenNavMenu} color="inherit">
              <MenuIcon />
            </IconButton>
            <Menu id="menu-appbar" anchorEl={anchorElNav} anchorOrigin={{ vertical: 'bottom', horizontal: 'left',}}
              keepMounted
              transformOrigin={{ vertical: 'top', horizontal: 'left', }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{display: { xs: 'block', md: 'none' }, }}>
                <MenuItem onClick={handleClickRecipesButton}>
                  <Typography textAlign="center">Recipes</Typography>
                </MenuItem>
                <MenuItem onClick={handleClickIngredientButton}>
                  <Typography textAlign="center">Ingredients</Typography>
                </MenuItem>
                {/* Render Alert if error message is present */}
                {message !== "" && <Alert severity="error">{message}</Alert>}
            </Menu>
          </Box>
          
          <CatchingPokemonIcon sx={{ display: { xs: 'flex', md: 'none' }, mr: 1 }} />
          <Typography variant="h5" noWrap component={Link} to="/"
            sx={{ mr: 2, display: { xs: 'flex', md: 'none' }, flexGrow: 1, fontFamily: 'monospace', fontWeight: 700, letterSpacing: '.3rem', color: 'inherit', textDecoration: 'none',}}>
            TasteTrove
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
              <Button sx={{ my: 2, color: 'white', display: 'block' }} onClick={handleClickRecipesButton}>Recipes</Button>
              <Button sx={{ my: 2, color: 'white', display: 'block' }} onClick={handleClickIngredientButton}>Ingredients</Button>
              {/* Render Alert if error message is present */}
              {message !== "" && <Alert severity="error" onClose={handleAlertClose}>{message}</Alert>}
          </Box>
          <Box sx={{ flexGrow: 0 }}>
            <Tooltip title="Open settings">
              <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }} aria-label="account of current user" color="inherit">
                <AccountCircle />
              </IconButton>
            </Tooltip>
            <Menu sx={{ mt: '45px' }} id="menu-appbar" anchorEl={anchorElUser} anchorOrigin={{ vertical: 'top', horizontal: 'right', }}
              keepMounted
              transformOrigin={{ vertical: 'top', horizontal: 'right', }}
              open={Boolean(anchorElUser)}
              onClose={handleCloseUserMenu}>
              <MenuItem><Typography textAlign="center" component={Link} to="/account">Account</Typography></MenuItem>
              <MenuItem><Typography textAlign="center" component={Link} to="/logout">Logout</Typography></MenuItem>
            </Menu>
          </Box>

        </Toolbar>
      </Container>
    </AppBar>
  );
}
export default ResponsiveAppBar;
