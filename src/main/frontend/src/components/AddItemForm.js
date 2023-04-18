import React, { useState } from 'react';
import axios from 'axios';
import ECategory from '../enums/ECategory';
import { getLoggedInUser } from '../auth/isAuthenticated';
import { FormControl, Select, MenuItem, TextField, Button } from "@mui/material";

const AddItemForm = () => {
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    state: '',
    file: null,
    category: 'FOODITEMS',
    username: getLoggedInUser()
  });

  const handleChange = (event) => {
    setFormData({
      ...formData,
      [event.target.name]: event.target.value,
    });
  };

  const handleFileChange = (event) => {
    setFormData({
      ...formData,
      file: event.target.files[0],
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const data = new FormData();
    data.append('title', formData.title);
    data.append('description', formData.description);
    data.append('state', formData.state);
    data.append('file', formData.file);
    data.append('category', formData.category);
    data.append('username', formData.username);

    axios.post('/api/items/add', data, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
      .then(response => {
        alert("Item successfully created!");
        window.location.reload();
      })
      .catch(error => {
        alert("ERROR");
      });
  };

  return (
    <div style={{display: 'flex', justifyContent: 'center' }}>
      <form onSubmit={handleSubmit}>
        <FormControl fullWidth >
          <TextField style={{marginBottom: '6px'}} label="Title" name="title" value={formData.title} onChange={handleChange} required fullWidth />
          <TextField style={{marginBottom: '6px'}} label="Description" name="description" value={formData.description} onChange={handleChange} required fullWidth />
          <TextField style={{marginBottom: '10px'}} label="State" name="state" value={formData.state} onChange={handleChange} required fullWidth />
          <input style={{marginBottom: '10px'}} type="file" name="file" onChange={handleFileChange} required fullWidth />
          <Select name="category" value={formData.category} onChange={handleChange} required fullWidth>
            {Object.values(ECategory).map(option => (
              <MenuItem key={option.value} value={option.value}>{option.displayName}</MenuItem>
            ))}
          </Select>
          <Button style={{margin: '10px 0'}} variant="contained" type="submit" fullWidth>Submit</Button>
        </FormControl>
      </form>
    </div>
  );
};

export default AddItemForm;
