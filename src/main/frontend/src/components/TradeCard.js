import React, { useState } from 'react';
import { Card, CardContent, Typography, Button } from '@mui/material';
import axios from 'axios';
import Chip from '@mui/material/Chip';

const TradeCard = ({ trade }) => {
  const [status, setStatus] = useState(trade.status);

  const handleAccept = () => {
    const confirmed = window.confirm('Are you sure you want to accept this trade?');
    if (confirmed) {
      axios.put(`/api/trades/update/${trade.id}`, { status: 'ACCEPTED' })
        .then(response => {
          console.log(response.data);
          setStatus('ACCEPTED');
        })
        .catch(error => {
          console.log(error);
        });
    }
  };

  const handleDecline = () => {
    const confirmed = window.confirm('Are you sure you want to decline this trade?');
    if (confirmed) {
      axios.put(`/api/trades/update/${trade.id}`, { status: 'DECLINED' })
        .then(response => {
          console.log(response.data);
          setStatus('DECLINED');
        })
        .catch(error => {
          console.log(error);
        });
    }
  };

  const formatDate = (date) => {
    return new Date(date).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  };

  const formatTime = (time) => {
    return new Date(time).toLocaleTimeString('en-US', {
      hour: 'numeric',
      minute: 'numeric',
      hour12: true
    });
  };

  return (
    <Card style={{ width:'300px', margin:'10px'}}>
      <Typography color="textSecondary">
        <code style={{fontSize: '11px', padding: '6px'}}>
          {formatDate(trade.date)} {formatTime(trade.date)}
        </code>
      </Typography>

      <CardContent>
        <div style={{display: 'flex', justifyContent:'space-between' }}>
          <Typography style={{fontSize: '13px'}} variant="h5" component="h2">
            <b>{trade.itemId} trade for {trade.offeredItemId}</b>
          </Typography>
          <Chip style={{opacity: '0.7'}} label={status} color={status === 'PENDING' ? 'default' : status === 'ACCEPTED' ? 'info' : 'warning'} />
        </div>
        <br/>
        <Typography variant="body2" component="p">
          {trade.comment}
        </Typography>
        
        <br/>
        {status === 'PENDING' && (
          <div style={{display: "flex", justifyContent: 'right'}}>
            <Button style={{marginRight: '6px', opacity: '0.9'}} variant="contained" color="success" onClick={handleAccept}>
              ACCEPT
            </Button>
            <Button style={{opacity: '0.9'}} variant="contained" color="error" onClick={handleDecline}>
              DECLINE
            </Button>
          </div>
        )}
      </CardContent>
    </Card>
  );
};

export default TradeCard;
