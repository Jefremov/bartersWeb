import React from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/HomePage';
import ItemsPage from './pages/ItemsPage';
import TradesPage from './pages/TradesPage';
import ResponsiveAppBar from './components/ResponsiveAppBar';
import ReviewsPage from './pages/ReviewsPage';
import AdminPage from './pages/AdminPage';
import UsersPage from './pages/UsersPage';
import LoginPage from './pages/LoginPage';
import PageNotFound from './pages/PageNotFound';

function App() {
  return (
      <Router>
      <ResponsiveAppBar/>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/items" element={<ItemsPage />} />
          <Route path="/trades" element={<TradesPage />} />s
          <Route path="/users" element={<UsersPage />} />
          <Route path="/reviews" element={<ReviewsPage />} />
          <Route path="/admin" element={<AdminPage />} />
          <Route path="*" element={<PageNotFound />} />
        </Routes>
      </Router>
  );
}

export default App;
