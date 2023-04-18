import jwt_decode from 'jwt-decode';

const isTokenExpired = (token) => {
  const decodedToken = jwt_decode(token);
  const currentTime = Date.now() / 1000;

  return decodedToken.exp < currentTime;
};

export const getLoggedInUser = () => {
  const token = localStorage.getItem('accessToken');

  if (token) {
    const decodedToken = jwt_decode(token);
    return decodedToken.sub;
  }

  return null;
};

export const isAuthenticated = () => {
  const token = localStorage.getItem('accessToken');

  if (token) {
    const isExpired = isTokenExpired(token);

    if (isExpired) {
        localStorage.removeItem('accessToken');
        return false;
    }

    return true;
  }

  return false;
};