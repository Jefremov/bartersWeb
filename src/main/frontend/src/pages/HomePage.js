import React from "react";
import { isAuthenticated, getLoggedInUser } from "../auth/isAuthenticated";
import { Container, Typography, Button, Grid, Card, CardContent, CardMedia } from '@mui/material';
import LinkedInIcon from '@mui/icons-material/LinkedIn';
import DoneIcon from '@mui/icons-material/Done';
import { Link } from "react-router-dom";


const developers = [
  {
    name: "Berta",
    role: "Manager",
    description: "\"Let me show you the way to success\"",
    linkedin: "https://www.linkedin.com/in/bertam/",
  },
  {
    name: "Elizabeth",
    role: "Visionary",
    description: "\"I see it, I'll code it\"",
    linkedin: "https://www.linkedin.com/in/elizabete-titane/"
  },
  {
    name: "Konstantin",
    role: "Hacker",
    description: "\"Let's use this approach\"",
    linkedin: "https://www.linkedin.com/in/konstantin-jefremov/"
  },
  {
    name: "Ralfs",
    role: "Fixer upper",
    description: "\"There is a problem, I'll fix it\"",
    linkedin: "https://www.linkedin.com/in/ralfsl"
   }
  ];


const Home = () => {

  return (
      <Container
        maxWidth="lg"
        style={{
            backgroundPosition: "center",
            minHeight: "100vh",
            marginTop: 30,
        }}
      >
                  <Button
            variant="contained"
            component={Link}
            to="/items"
            color="error"
            style={{ padding: '30px', size: 'large', margin: '0 auto', marginBottom: '20px' }}
          >
            FUN BEGINS HERE
          </Button>

        <Typography variant="h2" component="h1" gutterBottom>
          Welcome to BARTERS, <span style={{backgroundColor: '#f1f1f1'}}>{isAuthenticated() ? `${getLoggedInUser()}!` : " guest!"}</span>
        </Typography>
        <Typography variant="body1" gutterBottom style={{ marginBottom: 5 }}>
          <strong>BARTERS</strong> is a web application that aims to create a community where people can exchange goods and services without using money.
        </Typography>
        <Typography variant="body1" gutterBottom style={{ marginBottom: 5 }}>
        <DoneIcon />
          <i>The goal is to foster a culture of sharing and reduce waste by allowing people to trade items they no longer need for items they do need.</i>
        </Typography>
        <Typography variant="body1" gutterBottom>
          <DoneIcon />
          <i>The vision of BARTERS is to create a sustainable economy that values community and collaboration over competition and individualism.</i>
        </Typography>
      <Grid container spacing={3} style={{ marginTop: 30 }}>
        <Grid item xs={12}>
          <Typography variant="h4" component="h2" gutterBottom>
            Meet Our Developers
          </Typography>
        </Grid>
       <Grid container spacing={2}>
         {developers.map((developer, index) => (
           <Grid item xs={12} sm={6} md={3} key={index}>
             <Card style={{ height: '100%' }}>
               <Button variant="contained" color="primary" href={developer.linkedin} target="_blank" rel="noopener noreferrer" style={{marginBottom: '2px'}}>
                 <LinkedInIcon style={{color: 'white', marginRight: '5px'}} />
                 Connect on LinkedIn
               </Button>
               <CardMedia
                 style={{ height: 30 }}
                 title={developer.name}
               />
               <CardContent style={{ flexGrow: 1 }}>
                 <Typography gutterBottom variant="h5" component="h2">
                   {developer.name}
                 </Typography>
                 <Typography variant="body2" color="textSecondary" component="p">
                   {developer.role}
                 </Typography>
                 <Typography variant="body1" component="p">
                   {developer.description}
                 </Typography>
               </CardContent>
             </Card>
           </Grid>
         ))}
       </Grid>
      </Grid>
      </Container>
  );
};

export default Home;