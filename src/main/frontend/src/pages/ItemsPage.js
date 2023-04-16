import React, { useEffect, useState } from 'react';
import ItemCard from '../components/ItemCard';
import { Button } from '@mui/material';
import AddItemForm from '../components/AddItemForm';
import ECategory from '../enums/ECategory';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';

const Items = () => {
  const [allItems, setAllItems] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [categoryFilter, setCategoryFilter] = useState(null);

  const handleShowModalClick = () => {
    setShowModal(true);
  };

  const handleCategoryFilter = (category) => {
    setCategoryFilter(category);
  };

  useEffect(() => {
    const fetchData = async () => {
      const url = categoryFilter ? `/api/items/category/${categoryFilter}` : '/api/items';
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
  }, [categoryFilter]);

  console.log(allItems);

  return (
    <>
    <div style={{textAlign: 'center', marginBottom: '20px'}}>
      <Button style={{marginBottom: '10px'}} variant="contained" color="primary" onClick={handleShowModalClick}>Create Item</Button>
      <br/>
      {showModal && (
        <AddItemForm />
      )}
      <br/>
      <h/>
      <Grid container spacing={1}>
        <Grid item xs={12}>
          <Box display="flex" overflow="auto">
            {Object.values(ECategory).map((category) => (
              <Button
                key={category}
                style={{ marginRight: '10px', minWidth:'auto', pointer: 'cursor', color: 'black', borderColor: 'gray', fontSize: '11px' }}
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
    </div>
  
    {allItems?.length > 0 &&
      <div style={{ display: 'flex', flexWrap: 'wrap' }}>
        <ItemCard items={allItems} />
      </div>
    }

    {allItems?.length === 0 && (
      <div style={{textAlign: 'center', color: 'gray', marginTop: '20px'}}>
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
