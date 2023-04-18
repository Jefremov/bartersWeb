import React, { useEffect } from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/HomePage';
import ItemsPage from './pages/ItemsPage';
import TradesPage from './pages/TradesPage';
import ResponsiveAppBar from './components/ResponsiveAppBar';
import ReviewsPage from './pages/ReviewsPage';
import AddReviewForm from './components/AddReviewForm';
import AdminPage from './pages/AdminPage';
import UsersPage from './pages/UsersPage';
import LoginPage from './pages/LoginPage';
import PageNotFound from './pages/PageNotFound';
import { setAuthToken } from './auth/setAuthToken';
import { isAuthenticated } from './auth/isAuthenticated';

const App = () => {
  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    if (token) {
      setAuthToken(token);
    }
  }, []);

  return (
    <Router>
      <ResponsiveAppBar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/items" element={<ItemsPage />} />
        <Route path="/add-review" element={<AddReviewForm />} />
        {isAuthenticated() && <Route path="/my-items" element={<ItemsPage />} />}
        {isAuthenticated() && <Route path="/trades" element={<TradesPage />} />}
        {isAuthenticated() && <Route path="/users" element={<UsersPage />} />}
        {isAuthenticated() && <Route path="/reviews" element={<ReviewsPage />} />}
        {isAuthenticated() && <Route path="/admin" element={<AdminPage />} />}
        <Route path="*" element={<PageNotFound />} />
      </Routes>
    </Router>
  );
}

export default App;
