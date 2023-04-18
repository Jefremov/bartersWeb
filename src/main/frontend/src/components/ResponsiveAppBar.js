import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Container from '@mui/material/Container';
import Avatar from '@mui/material/Avatar';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import Box from '@mui/material/Box';
import Badge from '@mui/material/Badge';
import { Link, useNavigate } from 'react-router-dom';
import ReviewsIcon from '@mui/icons-material/Reviews';
import CategoryIcon from '@mui/icons-material/Category';
import HomeIcon from '@mui/icons-material/Home';
import ViewListIcon from '@mui/icons-material/ViewList';
import { isAuthenticated, getLoggedInUser } from "../auth/isAuthenticated";
import ArticleIcon from '@mui/icons-material/Article';
import LogoutIcon from '@mui/icons-material/Logout';
import { Divider } from '@mui/material';

const pages = [
  { name: 'Home', href: '/' , icon: <HomeIcon />},
  { name: 'Items', href: '/items', icon: <CategoryIcon /> },
  { name: 'Trades', href: '/trades', icon: <ViewListIcon /> },
  { name: 'Reviews', href: '/reviews', icon: <ReviewsIcon /> },

];
const settings = [
  { name: 'My items', link: '/my-items' },
  { name: 'Logout', link: 'login' }
];

function ResponsiveAppBar() {
  const navigate = useNavigate();
  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const [anchorElUser, setAnchorElUser] = React.useState(null);

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
  };

  return (
    <AppBar position="static" className='navigationMenu'>
      <Container maxWidth="xl" >
        <Toolbar disableGutters>
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="/"
            sx={{
              mr: 2,
              display: { xs: 'none', md: 'flex' },
              fontWeight: 100,
              letterSpacing: '.2rem',
              fontSize: '1.9rem',
              color: 'inherit',
              textDecoration: 'none',
              fontFamily: 'Verdana, sans-serif',
            }}
          >
           BARTERS
          </Typography>

          <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' }, }} >
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              color="inherit"
              onClick={handleOpenNavMenu}
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'left',
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{
                display: { xs: 'block', md: 'none' },
              }}
            >
              {pages.map((page, index) => (
                  <MenuItem key={index} onClick={handleCloseNavMenu} style={{display: "flex", alignContent: "center"}}>
                    <Link to={page.href} className='menuItemMobile'>
                      <span className='menuItemIcon'>{page.icon}</span>
                      <span>{page.name}</span>
                    </Link>
                  </MenuItem>
                ))}
                 <IconButton
              size="large"
              aria-label="show 17 new notifications"
              color="inherit"
            >

            </IconButton>
            </Menu>
          </Box>
          <Typography
            variant="h5"
            noWrap
            component="a"
            href=""
            sx={{
              mr: 2,
              display: { xs: 'flex', md: 'none' },
              flexGrow: 1,
              fontWeight: 300,
              letterSpacing: '.2rem',
              color: 'inherit',
              textDecoration: 'none',
              fontFamily: 'Verdana, sans-serif'
            }}
          >
            BARTERS
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }} style={{justifyContent: "flex-end"}}>

          {
            pages.map((page, index) => (
              <div key={index}>
                {page.name === 'Items' || page.name === 'Home' || isAuthenticated() ? (
                  <MenuItem
                    key={index}
                    onClick={handleCloseNavMenu}
                    style={{ display: 'flex', alignContent: 'center', color: '#FFF' }}
                  >
                    <Link to={page.href} className='menuItem'>
                      <span className='menuItemIcon'>{page.icon}</span>
                      <span>{page.name}</span>
                    </Link>
                  </MenuItem>
                ) : null}
              </div>
            ))
          }

          </Box>
          <Box>
            {
              false && (
                <Badge badgeContent={17} color="error">
                <ArticleIcon/>
              </Badge>
              )
            }
         

          </Box>

          <Box sx={{ flexGrow: 0 }} style={{display: 'flex', justifyContent: 'center', alignItems: 'center', padding:' 20px'}}>
            <div style={{marginRight: '6px'}}>
              {isAuthenticated() ? `${getLoggedInUser()}` : " Guest"}
            </div>

            <Tooltip title={isAuthenticated() ? "Open settings" : "Register"} >
              <IconButton onClick={isAuthenticated() ? handleOpenUserMenu : () => navigate('/login')} sx={{ p: 0 }}>
                <Avatar>
                  <AccountCircleIcon sx={{ color: "#FFF", fontSize: "44px" }} />
                </Avatar>
              </IconButton>
            </Tooltip>
            <Menu
              sx={{ mt: '45px', padding: '10px' }}
              id="menu-appbar"
              anchorEl={anchorElUser}
              anchorOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              open={Boolean(anchorElUser)}
              onClose={handleCloseUserMenu}
            >
            <div style={{padding: '16px'}}>
              {isAuthenticated() && (<div>Welcome back, <br/><b>{getLoggedInUser()}</b>!</div>)}
            </div>
            {settings.map((setting) => (
                  setting.name === 'Logout' ? (
                    <MenuItem key={setting.name}>
                      <Divider/>
                      <Link to={setting.link} onClick={handleLogout} style={{ textDecoration: 'none', color: '#000' }}>
                        <Typography textAlign="center" style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                        <LogoutIcon />  {setting.name}
                          </Typography>
                      </Link>
                    </MenuItem>
                  ) : (
                    <MenuItem key={setting.name} onClick={handleCloseUserMenu}>
                      <Link to={setting.link} style={{ textDecoration: 'none', color: '#000' }}>
                        <Typography textAlign="center">{setting.name}</Typography>
                      </Link>
                    </MenuItem>
                  )
                ))}
            </Menu>
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  );
}
export default ResponsiveAppBar;