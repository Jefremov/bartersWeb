import React, { useState, useEffect } from 'react';
import axios from 'axios';
import TradeCard from '../components/TradeCard';
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import TradeFilters from '../components/TradesFilter';

const Trades = () => {
  const [trades, setTrades] = useState([]);
  const [open, setOpen] = useState(false);
  const [selectedTrade, setSelectedTrade] = useState(null);
  const [selectedAction, setSelectedAction] = useState('');
  const [filteredTrades, setFilteredTrades] = useState([]);
  const [filter, setFilter] = useState('all');
  
  useEffect(() => {
    axios.get('/api/trades')
      .then(response => {
        const data = response.data;
        setTrades(data);
      })
      .catch(error => {
        console.log(error);
      });
  }, []);

  useEffect(() => {
    if (filter === 'all') {
      setFilteredTrades(trades);
    } else {
      const filtered = trades.filter(trade => trade.status === filter.toUpperCase());
      setFilteredTrades(filtered);
    }
  }, [trades, filter]);

  const handleFilter = (filter) => {
    setFilter(filter);
  };

  const handleConfirmation = (trade, action) => {
    setSelectedTrade(trade);
    setSelectedAction(action);
    setOpen(true);
  };

  const handleDialogClose = () => {
    setOpen(false);
    setSelectedTrade(null);
    setSelectedAction('');
  };

  const handleTradeUpdate = (tradeId, status) => {
    axios.put(`/api/trades/update/${tradeId}`, { status })
      .then(response => {
        const updatedTrade = response.data;
        setTrades(prevTrades => prevTrades.map(trade => trade.id === updatedTrade.id ? updatedTrade : trade));
        handleDialogClose();
      })
      .catch(error => {
        console.log(error);
      });
  };


  return ( 
    <div>
      <h4 style={{textAlign: 'center'}}>TRADES</h4>
      {/* <TradeFilters trades={trades} handleFilter={handleFilter} /> */}
      <div style={{ width: '100wh', display: 'flex', flexWrap: 'wrap' }}>
        {filteredTrades.map(trade => (
          <TradeCard key={trade.id} trade={trade} handleConfirmation={handleConfirmation} />
        ))}
      </div>
      
      <Dialog
        open={open}
        onClose={handleDialogClose}
        aria-labelledby="confirmation-dialog-title"
        aria-describedby="confirmation-dialog-description"
      >
        <DialogTitle id="confirmation-dialog-title">Confirmation</DialogTitle>
        <DialogContent>
          <DialogContentText id="confirmation-dialog-description">
            Are you sure you want to {selectedAction.toLowerCase()} this trade?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDialogClose}>Cancel</Button>
          <Button onClick={() => handleTradeUpdate(selectedTrade.id, selectedAction)} autoFocus>
            {selectedAction}
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  )
};

export default Trades;
