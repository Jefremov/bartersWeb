import React, { useState, useEffect } from 'react';
import ReviewCard from '../components/ReviewCard.js';
import { getLoggedInUser } from '../auth/isAuthenticated.js';

const Reviews = () => {
const [data, setData] = useState([]);
const [averageRating, setAverageRating] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch('/api/reviews/of/' + getLoggedInUser());
      const json = await response.json();
      setData(json);
    };
    fetchData();
  }, []);

  useEffect(() => {
      const fetchData = async () => {
        const response = await fetch('/api/reviews/' + getLoggedInUser() + '/average');
        const json = await response.json();
        setAverageRating(json);
      };
      fetchData();
    }, []);

  return data.length === 0 ? (
      <>
        <div style={{display:'flex', margin: '6px',  flexWrap: 'wrap', alignItems: 'center', justifyContent: 'center'}}>
          <p>No reviews found.</p>
        </div>
      </>
      ) : (
      <>
        <div style={{display:'flex', justifyContent: 'center'}}>Your average rating is: {averageRating}</div>
        <div style={{display:'flex', margin: '6px',  flexWrap: 'wrap', alignItems: 'center', justifyContent: 'center'}}>
          {data.map((review) => (review && <ReviewCard review={review} />))}
        </div>
      </>
      );
};

export default Reviews;