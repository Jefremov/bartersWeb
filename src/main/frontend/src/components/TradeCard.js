import React, { useState } from 'react';
import { Card, CardContent, Typography, Button, Divider } from '@mui/material';
import axios from 'axios';
import Chip from '@mui/material/Chip';
import Modal from '@mui/material/Modal';
import MultipleStopIcon from '@mui/icons-material/MultipleStop';

export const formatDate = (date) => {
  return new Date(date).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  });
};

export const formatTime = (time) => {
  return new Date(time).toLocaleTimeString('en-US', {
    hour: 'numeric',
    minute: 'numeric',
    hour12: true
  });
};

const config = {
  headers: {
    'Content-Type': 'multipart/form-data'
  }
};

const TradeCard = ({ trade }) => {
  const [status, setStatus] = useState(trade.status);
  const [showTradeModal, setShowTradeModal] = useState(false);
  const [item1, setItem1] = useState(null);
  const [item2, setItem2] = useState(null);

  const handleAccept = () => {
    const confirmed = window.confirm('Are you sure you want to accept this trade?');
    if (confirmed) {
      axios.put(`/api/trades/update/${trade.id}`, { status: 'ACCEPTED' }, config)
        .then(response => {
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
      axios.put(`/api/trades/update/${trade.id}`, { status: 'DECLINED' }, config)
        .then(response => {
          setStatus('DECLINED');
        })
        .catch(error => {
          console.log(error);
        });
    }
  };

  const handleModalOpen = () => {
    setShowTradeModal(true);
    axios.all([
      axios.get(`/api/items/${trade.itemId}`),
      axios.get(`/api/items/${trade.offeredItemId}`)
    ])
    .then(axios.spread((item1Res, item2Res) => {
      setItem1(item1Res.data);
      setItem2(item2Res.data);
    }))
    .catch(error => {
      console.log(error);
    });
  };

  const handleModalClose = () => {
    setShowTradeModal(false);
  };


  return (
    <>
      <Card style={{ width:'300px', margin:'10px', border: '1px, solid, gray', padding: '10px'}}>
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
          <Button style={{opacity: '0.9'}} variant="contained" color="error" onClick={handleModalOpen}>
            SHOW DETAILS
          </Button>
        </CardContent>
      </Card>

      <Modal open={showTradeModal} onClose={handleModalClose} style={{padding: '30px'}}>
        <div style={{position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%, -50%)', width: '400px', backgroundColor: 'white', boxShadow: 24, p: 4, padding: '20px'}}>
        <strong style={{paddingLeft: '8px'}} id="simple-modal-title">TRADE  DETAILS</strong>
          <div style={{display: 'flex', justifyContent: 'space-between', alignItems:"center", padding: '6px' }}>
          <code style={{fontSize: '11px', padding: '6px'}}>
            CREATED: {formatDate(trade?.date)} {formatTime(trade?.date)}
          </code>
            <div style={{fontSize: '11px'}}>
              <code style={{fontSize:'10px'}}>ID:{trade.id} </code>
              <Chip title="Trade Status" style={{opacity: '0.7'}} label={status} color={status === 'PENDING' ? 'default' : status === 'ACCEPTED' ? 'info' : 'warning'} />
            </div>
          </div>

          <section style={{display:'flex', justifyContent: 'space-between', alignItems: 'center'}}>
            <div style={{padding: '6px', margin: '6px'}}>
              <div style={{fontSize: '10px'}}>
                OWNER ID: {item1?.userId}
              </div>
                <img src={item1?.image} alt="item1" style={{width: '100px', height: '100px'}}/>
                <div>{item1?.title}</div>
                <Divider/>
                <br/>
                <div style={{fontSize: '11px'}}>
                  <strong>CONDITION</strong>
                  <div>{item1?.state}</div>
                </div>
                <br/>
                <div style={{fontSize: '11px'}}> 
                  <strong>DESCRIPTION</strong>
                  <div>{item1?.description}</div>
                  </div>
              </div>

            <div style={{padding: '6px', margin: '6px'}}>
              <MultipleStopIcon style={{fontSize: '100px', opacity: "0.1"}}/>

            </div>
            <div>
              <div style={{padding: '6px', margin: '6px'}}>
              <div style={{fontSize: '10px'}}>
                OWNER ID: {item2?.userId}
              </div>
                <img src={item2?.image} alt="item2" style={{width: '100px', height: '100px'}}/>
                <div> {item2?.title}</div>
                  <Divider/>
                  <br/>
                  <div style={{fontSize: '11px'}}>
                  <strong>CONDITION</strong>
                  <div>{item2?.state}</div>
                </div>
                  <br/>
                  <div style={{fontSize: '11px'}}> 
                  <strong>DESCRIPTION</strong>
                  <div>{item2?.description}</div>
                  </div>

            </div>
            </div>
          </section>
          <br/>
          <Divider/>
          <br/>

          {status === 'PENDING' && (
            <div style={{display: "flex", justifyContent: 'right'}}>
              <Button size='small' style={{marginRight: '6px', opacity: '0.9'}} variant="contained" color="success" onClick={handleAccept}>
                ACCEPT
              </Button>
              <Button size='small' style={{opacity: '0.9'}} variant="contained" color="error" onClick={handleDecline}>
                DECLINE
              </Button>
            </div>
          )}
          </div>  
      </Modal>

    </>
  );
};

export default TradeCard;
