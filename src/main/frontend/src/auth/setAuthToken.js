import axios from 'axios';

export const setAuthToken = token => {
   token = localStorage.getItem('accessToken');
   if (token) {
       axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
   }
   else
       delete axios.defaults.headers.common["Authorization"];
}