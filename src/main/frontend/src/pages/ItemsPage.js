import React, { useEffect, useState } from 'react';
import ItemCard from '../components/ItemCard';

const Items = () => {
  const [allItems, setAlItems] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch('/api/items');
      const json = await response.json();
      setAlItems(json);
    };
    fetchData();
  }, []);

  console.log('Items', allItems);

  return (
    <div style={{ display: 'flex', flexWrap: 'wrap' }}>
      <ItemCard items={allItems} />
    </div>
  );
}

export default Items;
