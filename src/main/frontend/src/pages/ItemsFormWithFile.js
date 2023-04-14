import React, { useState } from 'react';
import axios from 'axios';
import ECategory from '../enums/ECategory';

const AddItemForm = () => {
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    state: '',
    file: null,
    category: '',
    userId: '1',
    status: 'AVAILABLE',
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
    data.append('userId', formData.userId);
    data.append('status', formData.status);

    axios.post('/api/items/add', data, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
      .then(response => {
        alert("Item successfully added!");
      })
      .catch(error => {
        alert("ERROR");
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Title:
        <input type="text" name="title" value={formData.title} onChange={handleChange} required />
      </label><br/>
      <label>
        Description:
        <textarea name="description" value={formData.description} onChange={handleChange} required />
      </label><br/>
      <label>
        State:
        <input type="text" name="state" value={formData.state} onChange={handleChange} required />
      </label><br/>
      <label>
        File:
        <input type="file" name="file" onChange={handleFileChange} required />
      </label><br/>
      <label>
          Category:
          <select name="category" value={formData.category} onChange={handleChange} required>
            {Object.values(ECategory).map(option => (
              <option key={option.value} value={option.value}>{option.displayName}</option>
            ))}
          </select>
        </label><br/>
      <button type="submit">Submit</button>
    </form>
  );
};

export default AddItemForm;