import React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import Rating from '@mui/material/Rating';

const mapStringToRatingValue = (stringRating) => {
  switch(stringRating) {
    case "Fail":
      return 1;
    case "Poor":
      return 2;
    case "Average":
      return 3;
    case "Good":
      return 4;
    case "Excellent":
      return 5;
    default:
      return 0;
  }
}

const ReviewCard = ({review}) => {
  return (
    <Card sx={{ width:'300px', margin: '6px'}}>
      <CardContent>
        <Typography variant="h5" component="h2">
         <b>{review?.reviewed}</b>
        </Typography>
         <Rating name="read-only" value={mapStringToRatingValue(review?.grade)} readOnly />
        <div><b>Reviewed: {review?.reviewer}</b></div>
        <Typography variant="body2" component="p">
          REVIEW: {review?.comment}
        </Typography>
      </CardContent>
    </Card>
  );
};

export default ReviewCard;
