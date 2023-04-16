import React, { useEffect, useState } from 'react';
import ItemCard from '../components/ItemCard';
import { Button } from '@mui/material';
import AddItemForm from '../components/AddItemForm';

const Items = () => {
  const [allItems, setAllItems] = useState([]);
  const [showModal, setShowModal] = useState(false);

  const handleShowModalClick = (itemObj) => {
    setShowModal(true);
  };

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch('/api/items');
      const json = await response.json();
      setAllItems(json);
    };
    fetchData();
  }, []);

  console.log('Items', allItems);

  return (
    <>
    <div style={{textAlign: 'center', marginBottom: '20px'}}>
      <Button style={{marginBottom: '10px'}} variant="contained" color="primary" onClick={handleShowModalClick}>Create Item</Button>
      <br/>
      {showModal && (
        <AddItemForm />
      )}
      <br/>
    </div>
      <div style={{ display: 'flex', flexWrap: 'wrap' }}>
        <ItemCard items={allItems} />
      </div>

    </>
  );
}

export default Items;
