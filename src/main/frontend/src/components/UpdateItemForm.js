import { useState } from "react";
import { FormControl, Select, MenuItem, TextField, Button } from "@mui/material";
import ECategory from "../enums/ECategory";
import { getLoggedInUser } from "../auth/isAuthenticated";

const UpdateForm = ({ data, onUpdate }) => {
  let selectedCategoryValue;
  for (const categoryKey in ECategory) {
    if (ECategory[categoryKey].displayName === data.category) {
      selectedCategoryValue = ECategory[categoryKey].value;
      break;
    }
  }

  const [formData, setFormData] = useState({
    title: data.title,
    description: data.description,
    category: selectedCategoryValue,
    state: data.state,
    file: data.file,
    username: getLoggedInUser(),
    imageChange: false,
    status: data.status.toUpperCase(),
  });

  const handleSelectChange = (event) => {
      const { name, value } = event.target;
      setFormData({ ...formData, [name]: value });
    };

  const handleFormSubmit = (e) => {
    e.preventDefault();
    onUpdate(formData);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleFileChange = (event) => {
    setFormData({
      ...formData,
      file: event.target.files[0],
      imageChange: true,
    });
  };

  return (
    <form onSubmit={handleFormSubmit} style={{ width: "100%" }}>
      <FormControl fullWidth>
        <TextField name="title" value={formData.title} onChange={handleInputChange} label="Title" variant="outlined" margin="normal" fullWidth />
        <TextField name="description" value={formData.description} onChange={handleInputChange} label="Description" variant="outlined" margin="normal" fullWidth />
        <TextField name="state" value={formData.state} onChange={handleInputChange} label="Condition" variant="outlined" margin="normal" fullWidth />
        <Select name="category" value={formData.category} onChange={handleSelectChange} fullWidth>
          {Object.values(ECategory).map((option) => (
            <MenuItem key={option.value} value={option.value}>
              {option.displayName}
            </MenuItem>
          ))}
        </Select>
        <input type="file" onChange={handleFileChange} style={{ marginTop: "15px" }} />
        <Button type="submit" variant="contained" color="primary" style={{ marginTop: "15px" }}>
          Update
        </Button>
      </FormControl>
    </form>
  );
}

export default UpdateForm;