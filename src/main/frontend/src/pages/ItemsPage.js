import React, { useEffect, useState } from 'react';
import ItemCard from '../components/ItemCard';
import { Button, Divider } from '@mui/material';
import AddItemForm from '../components/AddItemForm';
import ECategory from '../enums/ECategory';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import InputBase from '@mui/material/InputBase';
import IconButton from '@mui/material/IconButton';
import SearchIcon from '@mui/icons-material/Search';
import axios from 'axios';
import ClearIcon from '@mui/icons-material/Clear';
import { useLocation } from 'react-router-dom';
import { isAuthenticated, getLoggedInUser } from '../auth/isAuthenticated';

const Items = () => {
  const [allItems, setAllItems] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [categoryFilter, setCategoryFilter] = useState(null);
  const [searchQuery, setSearchQuery] = useState('');
  const location = useLocation();

  useEffect(() => {
    const fetchData = async () => {
      const url = isAuthenticated() && location.pathname === '/my-items'
        ? `/api/items/user/${getLoggedInUser()}`
        : (categoryFilter ? `/api/items/category/${categoryFilter}` : '/api/items');
      try {
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const json = await response.json();
        setAllItems(json);
      } catch (error) {
        console.error('Error fetching data:', error);
        setAllItems([]);
      }
    };
    fetchData();
  }, [categoryFilter, location.pathname]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`/api/items/search?title=${searchQuery}`);
        setAllItems(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    if (!(isAuthenticated() && location.pathname === '/my-items')) { fetchData(); }
  }, [searchQuery, location.pathname]);

  const handleShowModalClick = () => {
    setShowModal(true);
  };

  const handleCategoryFilter = (category) => {
    setCategoryFilter(category);
  };

  const handleSearchQuery = (event) => {
    setSearchQuery(event.target.value);
    };

  const handleItemSearch = () => {
    console.log('Search button clicked with search text:', searchQuery);
      try {
        const response = axios.get(`/api/items/search?title=${searchQuery}`);
        setAllItems(response.data);
      } catch (error) {
        console.log(error);
      }
  };

  const clearFilter = () => {
    setCategoryFilter(null);
    setSearchQuery('');
  };


  return (
    <>
    <div style={{textAlign: 'center', marginBottom: '20px'}}>
    {(isAuthenticated() && location.pathname === '/my-items') ? (
    <>
      <Button style={{marginBottom: '10px'}} variant="contained" color="primary" onClick={handleShowModalClick}>Create Item</Button>
      <br/>
      {showModal && (
        <>
          <AddItemForm />
          <Button style={{marginBottom: '10px'}} variant="outlined" color="error" onClick={() => setShowModal(false)}>X</Button>
        </>
      )}
      <br/>
    </>
      ) : (
    <>
      <Paper component="form">
        <InputBase
          placeholder="Search items by title"
          inputProps={{ 'aria-label': 'Search items by title' }}
          value={searchQuery}
          onChange={handleSearchQuery}
        />
        <IconButton
          type="submit"
          aria-label="search"
          onClick={handleItemSearch}
        >
          <SearchIcon />
        </IconButton>
      </Paper>
      <h/>
      <br/>
      <Grid container spacing={1}>
        <Grid item xs={12}>
          <Box display="flex" overflow="auto">
            {Object.values(ECategory).map((category) => (
              <Button
                key={category}
                style={{ marginRight: '10px', minWidth:'auto', pointer: 'cursor', color: 'black', borderColor: 'gray', fontSize: '11px', whiteSpace: 'nowrap' }}
                variant={categoryFilter === category ? 'contained' : 'outlined'}
                onClick={() => handleCategoryFilter(category.value)}
              >
                {category.displayName}
              </Button>
            ))}
          </Box>
        </Grid>
      </Grid>
        <h/>
        </>
        )}
    </div>
    <Divider />
    <br/>
    {((searchQuery || categoryFilter) && !(isAuthenticated() && location.pathname === '/my-items')) && (
        <div style={{textAlign: 'center'}}>
          <Button color='error' type='button' onClick={clearFilter} startIcon={<ClearIcon />}>
            Clear Filters
          </Button>
        </div>
      )}


    {allItems?.length > 0 &&
      <div style={{ display: 'flex', flexWrap: 'wrap', justifyContent: "center" }}>
        <ItemCard items={allItems} />
      </div>
    }

    {allItems?.length === 0 && (
      <div style={{textAlign: 'center', color: 'gray', marginTop: '60px'}}>
        No items in this category, yet.
        <br/><br/>
        Would you like to add one? 
        <br/><br/>
        <Button style={{marginBottom: '10px'}} variant="contained" color="primary" onClick={handleShowModalClick}>Create Item</Button>
      </div>
    )}
    </>
  );
}

export default Items;
