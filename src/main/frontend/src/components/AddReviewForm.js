import React, { useState } from "react";
import axios from "axios";
import { Box, Button, FormControl, FormHelperText, Grid, Rating, TextField, Typography } from "@mui/material";
import { getLoggedInUser } from "../auth/isAuthenticated";

const mapRatingToStringValue = (rating) => {
  switch(rating) {
    case 1:
      return "FAIL";
    case 2:
      return "POOR";
    case 3:
      return "AVERAGE";
    case 4:
      return "GOOD";
    case 5:
      return "EXCELLENT";
    default:
      return 0;
  }
}

const AddReviewForm = () => {
    const [formData, setFormData] = useState({
        grade: '',
        comment: '',
        reviewer: getLoggedInUser()
      });
      let reviewedUser = 'user1';

      const handleRatingChange = (event, newValue) => {
        setFormData({ ...formData, grade: newValue });
      };
      const handleInputChange = (event) => {
        setFormData({ ...formData, [event.target.name]: event.target.value });
      };

  const handleSubmit = (event) => {
      event.preventDefault();

      const data = new FormData();
      data.append('grade', mapRatingToStringValue(formData.grade));
      data.append('comment', formData.comment);
      data.append('reviewer', formData.reviewer);
      console.log(data);

      axios.post(`/api/reviews/of/${reviewedUser}`, data, {
        headers: {
          'Content-Type': 'multipart/form-data'
        },
      })
        .then(response => {
          alert("Review successfully added!");
          window.location.reload();
        })
        .catch(error => {
          alert("ERROR");
        });
    };

  return (
      <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center" }}>
        <Typography variant="h5" gutterBottom>
          Leave a Review
        </Typography>
        <Box sx={{ width: "100%", maxWidth: 600 }}>
          <form onSubmit={handleSubmit} style={{ width: "100%" }}>
            <FormControl sx={{ width: "100%" }}>
              <Rating name="grade" value={formData.grade} onChange={handleRatingChange} />
              <TextField name="comment" value={formData.comment} onChange={handleInputChange} label="Review" variant="outlined" margin="normal" minRows={3} fullWidth multiline />
              <Button type="submit" variant="contained" color="primary" style={{ marginTop: "15px" }}>
                Submit
              </Button>
            </FormControl>
          </form>
        </Box>
      </Box>
  );
};

export default AddReviewForm;