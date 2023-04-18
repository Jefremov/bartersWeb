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
  const [showModal, setShowModal] = useState(false);
  const [categoryFilter, setCategoryFilter] = useState(null);
  const [searchQuery, setSearchQuery] = useState('');
  const location = useLocation();
  const [userItems, setUserItems] = useState([]);
  const [otherItems, setOtherItems] = useState([]);
  const [items, setItems] = useState([]);
  const loggedInUser = getLoggedInUser();

  useEffect(() => {
    const fetchUserItems = async () => {
      const loggedInUser = getLoggedInUser();
      if (!loggedInUser) {
        setUserItems([]);
        return;
      }
  
      const url = `/api/items/user/${loggedInUser}`;
  
      try {
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error(`Failed to fetch user items. Status code: ${response.status}`);
        }
  
        const data = await response.json();
        setUserItems(data);
      } catch (error) {
        console.error('Error fetching user items:', error);
        setUserItems([]);
      }
    };
  
    fetchUserItems();
  }, [loggedInUser]);
  

  useEffect(() => {
    const fetchOtherItems = async () => {
      if (!loggedInUser) {
        setOtherItems([]);
        return;
      }
  
      const url = `/api/items/not/${loggedInUser}`;
  
      try {
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error(`Failed to fetch other items. Status code: ${response.status}`);
        }
  
        const data = await response.json();
        setOtherItems(data);
      } catch (error) {
        console.error('Error fetching other items:', error);
        setOtherItems([]);
      }
    };
  
    fetchOtherItems();
  }, [loggedInUser]);
  
  console.log('OTHER ITEMS >>> PAGE', otherItems);

  useEffect(() => {
    const fetchUserItems = async () => {
      const loggedInUser = getLoggedInUser();
      if (!loggedInUser) {
        setUserItems([]);
        return;
      }
  
      const url = `/api/items/user/${loggedInUser}`;
  
      try {
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error(`Failed to fetch user items. Status code: ${response.status}`);
        }
  
        const data = await response.json();
        setUserItems(data);
      } catch (error) {
        console.error('Error fetching user items:', error);
        setUserItems([]);
      }
    };
  
    fetchUserItems();
  }, [loggedInUser]);
  
  console.log('USER ITEMS >>> PAGE', userItems);

  useEffect(() => {
    const fetchItems = async () => {
      const url = '/api/items';
  
      try {
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error(`Failed to fetch items. Status code: ${response.status}`);
        }
  
        const data = await response.json();
        setItems(data);
      } catch (error) {
        console.error('Error fetching items:', error);
        setItems([]);
      }
    };
  
    fetchItems();
  }, []);
  
  console.log('ALL ITEMS', items);  

  useEffect(() => {
    const fetchData = async () => {
      const url = categoryFilter ? `/api/items/category/${categoryFilter}` : '/api/items';
      try {
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const json = await response.json();
        setItems(json);
      } catch (error) {
        console.error('Error fetching data:', error);
        setItems([]);
      }
    };
    fetchData();
  }, [categoryFilter]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`/api/items/search?title=${searchQuery}`);
        setItems(response.data);
      } catch (error) {
        console.log(error);
      }
    };
  
    fetchData();
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

    const handleItemSearch = async () => {
      console.log('Search button clicked with search text:', searchQuery);
      try {
        const response = await axios.get(`/api/items/search?title=${searchQuery}`);
        setItems(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    
    // useEffect(() => {
    //   if (searchQuery !== '') {
    //     handleItemSearch();
    //   }
    // }, [searchQuery]);
    

  const clearFilter = () => {
    setCategoryFilter(null);
    setSearchQuery('');
  };

  console.log('search query', searchQuery);


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
      <div style={{ display: 'flex', flexWrap: 'wrap', justifyContent: "center" }}>
        <ItemCard items={userItems} userItems={null} />
      </div>
    </>
      ) : (
    <>
    {!isAuthenticated() && (
      <>
      {((searchQuery || categoryFilter)) && (
          <div style={{textAlign: 'center'}}>
            <Button color='error' type='button' onClick={clearFilter} startIcon={<ClearIcon />}>
              Clear Filters
            </Button>
          </div>
        )}

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

        <div style={{ display: 'flex', flexWrap: 'wrap', justifyContent: "center" }}>
          <ItemCard items={items} userItems={null} />
        </div>

        {(items?.length === 0) && (
          <div style={{textAlign: 'center', color: 'gray', marginTop: '60px'}}>
            No items in this category, yet.
          </div>
        )} 
      </>
      )}

    {isAuthenticated() && (
      <>
        <div style={{ display: 'flex', flexWrap: 'wrap', justifyContent: "center" }}>
          <ItemCard items={otherItems} userItems={userItems} />
        </div>
      </>
    )}
      <h/>
      <br/>
        <h/>
        </>
        )}
    </div>
    <Divider />
    <br/>
    </>
  );
}

export default Items;
