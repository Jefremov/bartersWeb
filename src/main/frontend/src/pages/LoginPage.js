import React, { useState } from 'react';
import LoginForm from '../components/LoginForm';
import RegisterForm from '../components/RegisterForm';

const Login = () => {
  const [isRegistered, setIsRegistered] = useState(false);

  const handleRegister = () => {
    setIsRegistered(true);
  }

  const handleLogin = () => {
    setIsRegistered(false);
  }

  return (
    <div style={{marginTop: "60px"}}>
      {isRegistered ? (
        <div>
          <div style={{textAlign: "center"}}>
            <div>Don't have an account?</div>
            <div>
              <button onClick={handleLogin}>Register</button>
            </div>
          </div>
          <LoginForm onRegister={handleRegister} />
        </div>
      ) : (
        <div>
          <div style={{textAlign: "center"}}>
            <div>Already have an account? </div>
            <div>
              <button onClick={handleRegister}>Login</button>
            </div>
            </div>
          <RegisterForm onLogin={handleLogin} />
        </div>
      )}
    </div>
  );
};

export default Login;
