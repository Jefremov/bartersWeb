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

const refreshAccessToken = async () => {
  const refreshToken = localStorage.getItem('refreshToken');

  if (refreshToken) {
    try {
      const response = await fetch('/api/refresh-token', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${refreshToken}`,
        },
      });

      if (response.ok) {
        const data = await response.json();
        localStorage.setItem('accessToken', data.accessToken);
        localStorage.setItem('refreshToken', data.refreshToken);
        return true;
      }
    } catch (error) {
      console.error('Error refreshing token:', error);
    }
  }

  return false;
};

export const isAuthenticated = () => {
  const token = localStorage.getItem('accessToken');

  if (token) {
    const isExpired = isTokenExpired(token);

    if (isExpired) {
    /*
      const refreshed = await refreshAccessToken();
      if (refreshed) {
        return true;
      } else {
      */
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        return false;
      //}
    }

    return true;
  }

  return false;
};