import React from 'react';
import './App.css';

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/HomePage';
import Items from './pages/ItemsPage';
import Trades from './pages/TradesPage';
import Layout from './components/Navigation';
import TestComponent from './pages/TestComponent';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Layout />}>
            <Route path="/" element={<Home />} />
            <Route path="/items" element={<Items />} />
            <Route path="/trades" element={<Trades />} />
            <Route path="/test" element={<TestComponent />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
