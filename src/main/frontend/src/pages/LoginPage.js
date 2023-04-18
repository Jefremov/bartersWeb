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

    {!isRegistered && (
        <div>
          <LoginForm onRegister={handleRegister} />

          <div style={{textAlign: "center"}}>
            <div>Don't have an account?</div>
            <div>
              <button onClick={() => setIsRegistered(true)}>Register</button>
            </div>
          </div>
        </div>
      )}

      {isRegistered && (
        <div>
          <RegisterForm onLogin={handleLogin} />

          <div style={{textAlign: "center"}}>
            <div>Already have an account? </div>
            <div>
              <button onClick={() => setIsRegistered(false)}>Login</button>
            </div>
            </div>
        </div>
      )}

    </div>
  );
};

export default Login;
