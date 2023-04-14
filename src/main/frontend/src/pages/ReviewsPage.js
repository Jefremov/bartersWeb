import React, { useState, useEffect } from 'react';
import ReviewCard from '../components/ReviewCard.js';

const Reviews = () => {
const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch('/api/reviews');
      const json = await response.json();
      setData(json);
    };
    fetchData();
  }, []);

  return (
    <div style={{display:'flex', margin: '6px',  flexWrap: 'wrap', alignItems: 'center', justifyContent: 'center'}}>
      {data.map((review) => (review && <ReviewCard review={review} />))}
    </div>);
};

export default Reviews;