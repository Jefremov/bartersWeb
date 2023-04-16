import React from "react";
import { isAuthenticated, getLoggedInUser } from "../auth/isAuthenticated";

const Home = () => {
  return (
      <div>
        {isAuthenticated() ? (
          <h1>Hello, {getLoggedInUser()}!</h1>
        ) : (
          <h1>Hello guest!</h1>
        )}
      </div>
    );
};

export default Home;