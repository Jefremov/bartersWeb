import React, { useState, useEffect } from 'react';
import Rating from '@mui/material/Rating';

function ItemOwnerInfo({ itemId }) {
  const [itemOwner, setItemOwner] = useState(null);

  useEffect(() => {
    const fetchItemOwner = async () => {
      const response = await fetch(`/api/items/${itemId}/owner`);
      const itemOwner = await response.json();
      setItemOwner(itemOwner);
    };

    fetchItemOwner();
  }, [itemId]);

  if (!itemOwner) {
    return <div>Loading...</div>;
  }

  return (
    <>
      <div style={{ margin: '10px 0px 5px 0px' }}>Owner: {itemOwner.username}</div>
      <div style={{ display: 'flex' }}>
          <Rating name="rating-display" value={itemOwner.rating} precision={0.05} readOnly />
          <div style={{ position: 'relative', left: '10px', width: '50px' }}>{itemOwner.rating}</div>
      </div>
    </>
  );
}

export default ItemOwnerInfo;