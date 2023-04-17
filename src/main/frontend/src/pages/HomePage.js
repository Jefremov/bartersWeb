import React from "react";
import { isAuthenticated, getLoggedInUser } from "../auth/isAuthenticated";
import { Container, Typography, Button, Grid, Card, CardContent, CardMedia } from '@mui/material';
import LinkedInIcon from '@mui/icons-material/LinkedIn';

const developers = [  {    name: "Berta",    role: "Manager",    description: "\"Burnouts cause incompetence\"", linkedin: "https://www.linkedin.com/in/bertam/", },  {    name: "Elizabeth",    role: "Visionary",    description: "\"I see it, i'll code it\"",linkedin: "https://www.linkedin.com/in/elizabete-tit%C4%81ne-074646272/",  },  {    name: "Konstantin",    role: "Hacker",    description: "\"Let's use this approach\"", linkedin: "https://www.linkedin.com/in/konstantin-jefremov-38429322a", },  {    name: "Ralfs",    role: "Fixer upper",    description: "\"There is a problem, i'll fix it\"", linkedin: "https://www.linkedin.com/in/ralfs-%C4%BCaksa-9008b7272", },];


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
        <Typography variant="h2" component="h1" gutterBottom>
          Welcome to our Bartering App, dear {isAuthenticated() ? `${getLoggedInUser()}!` : " guest!"}
        </Typography>
        <Typography variant="body1" gutterBottom style={{ marginBottom: 5 }}>
          BARTERS is a web application that aims to create a community where people can exchange goods and services without using money.
        </Typography>
        <Typography variant="body1" gutterBottom style={{ marginBottom: 5 }}>
          The goal is to foster a culture of sharing and reduce waste by allowing people to trade items they no longer need for items they do need.
        </Typography>
        <Typography variant="body1" gutterBottom>
          The vision of BARTERS is to create a sustainable economy that values community and collaboration over competition and individualism.
        </Typography>
      <Grid container spacing={3} style={{ marginTop: 30 }}>
        <Grid item xs={12}>
          <Typography variant="h4" component="h2" gutterBottom>
            Meet Our Developers
          </Typography>
        </Grid>
      {developers.map((developer, index) => (
        <Grid item xs={12} sm={6} md={3} key={index}>
          <Card>
            <Button variant="contained" color="primary" href={developer.linkedin} target="_blank" rel="noopener noreferrer" style={{marginBottom: '10px'}}>
              <LinkedInIcon style={{color: 'white', marginRight: '5px'}} />
              Connect on LinkedIn
            </Button>
            <CardMedia
              style={{ height: 30 }}
              title={developer.name}
            />
            <CardContent>
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
      </Container>
  );
};

export default Home;