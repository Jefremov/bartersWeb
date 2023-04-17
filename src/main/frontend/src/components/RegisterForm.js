import React, { useState } from 'react';
import axios from 'axios';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import CheckBoxIcon from '@mui/icons-material/CheckBox';


const theme = createTheme();

const SignUp = () => {
  const [success, setSuccess] = useState(false);
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    phoneNumber: '',
    password: '',
  });

  const [errors, setErrors] = useState({
    username: '',
    email: '',
    phoneNumber: '',
    password: '',
  });


  const handleChange = (event) => {
    setFormData({
        ...formData,
        [event.target.name]: event.target.value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    data.append('username', formData.username);
    data.append('email', formData.email);
    data.append('phoneNumber', formData.phoneNumber);
    data.append('password', formData.password);

    axios.post('/api/register', formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    })
    .then(response => {
        setSuccess(true);
        setErrors([])
    })
    .catch(error => {
      setErrors(error.response.data);
    });
  };

  const errorMessages = (fieldTitle, errors) => {
    const fieldErrors = [];
    if (errors !== undefined) {
      errors?.violations?.forEach(violation => {
        if (violation?.fieldName === fieldTitle) {
          fieldErrors.push(
            <div key={violation.message} style={{color: violation.message ? 'red' : ''}}>
              {violation.message}
            </div>
          );
        }
      });
    }
    return fieldErrors;
  };
  
  

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
      {success && (
        <Box style={{textAlign: 'center'}} >
          <Typography component="h1" variant="h5">
            <CheckBoxIcon style={{opacity: '0.4', color: 'lightGreen', fontSize: '260px' }} />
            <div>Registration successful!</div>
            <br></br>
          </Typography>
        </Box>
      )}
        {
          !success && (
            <>
                <Box
                  sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                  }}
                >

                  <Avatar sx={{ m: 1, bgcolor: 'black' }}>
                    <LockOutlinedIcon />
                  </Avatar>
                  <Typography component="h1" variant="h5">
                    Register
                  </Typography>
                  <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
                    <Grid container spacing={2}>
                      <Grid item xs={12} sm={6}>
                        <TextField
                          autoComplete="given-name"
                          name="username"
                          value={formData.username}
                          onChange={handleChange}
                          required
                          fullWidth
                          id="username"
                          label="Username"
                          autoFocus
                          error={errorMessages('username', errors) ? true : false}
                          helperText={errorMessages('username', errors)}
                        />
                      </Grid>
                      <Grid item xs={12} sm={6}>
                        <TextField
                          required
                          fullWidth
                          id="email"
                          label="Email Address"
                          name="email"
                          value={formData.email}
                          onChange={handleChange}
                          autoComplete="email"
                          error={errorMessages('email', errors) ? true : false}
                          helperText={errorMessages('email', errors)}

                        />
                      </Grid>
                      <Grid item xs={12}>
                        <TextField
                          required
                          fullWidth
                          id="phoneNumber"
                          label="Phone Number"
                          name="phoneNumber"
                          value={formData.phoneNumber}
                          onChange={handleChange}
                          autoComplete="phoneNumber"
                          error={errorMessages('phoneNumber', errors) ? true : false}
                          helperText={errorMessages('phoneNumber', errors)}

                        />
                      </Grid>
                      <Grid item xs={12}>
                        <TextField
                          required
                          fullWidth
                          name="password"
                          value={formData.password}
                          onChange={handleChange}
                          label="Password"
                          type="password"
                          id="password"
                          autoComplete="new-password"
                          error={errorMessages('password', errors) ? true : false}
                          helperText={errorMessages('password', errors)}

                        />
                      </Grid>
                    </Grid>
                    <Button
                      type="submit"
                      fullWidth
                      variant="contained"
                      sx={{ mt: 3, mb: 2 }}
                    >
                      Sign Up
                    </Button>
                  </Box>
                </Box>
            </>
          )
        }
        <CssBaseline />
      </Container>
    </ThemeProvider>
  );
}

export default SignUp;