import React, { useEffect, useState } from 'react';

function Items() {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch('/api/items');
      const json = await response.json();
      setData(json);
    };
    fetchData();
  }, []);

  return (
    <div>
      {data.map((item) => (
        <div key={item.id}>
          <h2>{item.title}</h2>
          <p>{item.description}</p>
          <p>{item.category}</p>
        </div>
      ))}
    </div>
  );
}

export default Items;