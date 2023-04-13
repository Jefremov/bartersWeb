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
import NotificationsIcon from '@mui/icons-material/Notifications';
import MailIcon from '@mui/icons-material/Mail';
import { Link } from 'react-router-dom';
import PeopleAltIcon from '@mui/icons-material/PeopleAlt';
import AdminPanelSettingsIcon from '@mui/icons-material/AdminPanelSettings';
import ReviewsIcon from '@mui/icons-material/Reviews';
import CategoryIcon from '@mui/icons-material/Category';
import HomeIcon from '@mui/icons-material/Home';
import ViewListIcon from '@mui/icons-material/ViewList';

const pages = [
  { name: 'Home', href: '/' , icon: <HomeIcon />},
  { name: 'Items', href: '/items', icon: <CategoryIcon /> },
  { name: 'Trades', href: '/trades', icon: <ViewListIcon /> },
  { name: 'Users', href: '/users', icon: <PeopleAltIcon />},
  { name: 'Reviews', href: '/reviews', icon: <ReviewsIcon /> },
  { name: 'Admin', href: '/admin', icon: <AdminPanelSettingsIcon />}

];
const settings = ['Profile', 'Dashboard', 'Support', 'Logout'];

function ResponsiveAppBar() {
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
              onClick={handleOpenNavMenu}
              color="inherit"
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
                      {console.log(page)}
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
              letterSpacing: '.5rem',
              color: 'inherit',
              textDecoration: 'none',
              fontFamily: 'Verdana, sans-serif'
            }}
          >
            BARTERS
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }} style={{justifyContent: "flex-end"}}>
            {pages.map((page, index) => (
                  <MenuItem key={index} onClick={handleCloseNavMenu} style={{display: "flex", alignContent: "center", color: "#FFF"}}>
                    <Link to={page.href} className='menuItem'>
                      <span className='menuItemIcon'>{page.icon}</span>
                      <span>{page.name}</span>
                    </Link>
                  </MenuItem>
                ))}
          </Box>

          <Box>
          <Badge badgeContent={17} color="error">
            <NotificationsIcon />
          </Badge>

          <IconButton size="large" aria-label="show 4 new mails" color="inherit">
              <Badge badgeContent={4} color="error">
                <MailIcon />
              </Badge>
            </IconButton>
          </Box>

          <Box sx={{ flexGrow: 0 }}>
            <Tooltip title="Open settings">
              <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                <Avatar>
                  <AccountCircleIcon sx={{ color: "#FFF", fontSize: "44px" }} />
                </Avatar>
              </IconButton>
            </Tooltip>
            <Menu
              sx={{ mt: '45px' }}
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
              {settings.map((setting) => (
                <MenuItem key={setting} onClick={handleCloseUserMenu}>
                  <Typography textAlign="center">{setting}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  );
}
export default ResponsiveAppBar;