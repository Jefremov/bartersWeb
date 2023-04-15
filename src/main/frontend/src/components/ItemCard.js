import React, { useState, memo } from 'react';
import { Paper, Typography, Button, Modal, Box, Divider } from '@mui/material';
import FormControl from '@mui/material/FormControl';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Textarea from '@mui/material/TextareaAutosize';
import axios from 'axios';

const ItemCard = ({ items }) => {
  const [showMoreInfoDialog, setShowMoreInfoDialog] = useState(false);
  const [showCreateTradeModal, setShowCreateTradeModal] = useState(false);
  const [selectedItem, setSelectedItem] = useState(null);
  const [offeredItemId, setOfferedItemId] = React.useState('');
  const [text, setText] = React.useState('');

  const handleShowMoreInfoClick = (itemObj) => {
    setSelectedItem(itemObj);
    setShowMoreInfoDialog(true);
  };

  const handleShowMoreInfoClose = () => {
    setShowMoreInfoDialog(false);
  };

  const handleCreateTradeClick = (itemObj) => {
    setSelectedItem(itemObj);
    setShowCreateTradeModal(true);
  };

  const handleCreateTradeClose = () => {
    setShowCreateTradeModal(false);
  };

  const handleCreateTradeFormSubmit = () => {
    const config = {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    };
  
    axios.post('api/trades/create', {
      itemId: selectedItem.id,
      offeredItemId: offeredItemId,
      text: text
    }, config)
    .then(response => {
      console.log('Trade created successfully:', response.data);
      // add success popup
      handleCreateTradeClose();
    })
    .catch(error => {
      console.error('Error creating trade:', error);
    });
  
    console.log('offeredItemId', offeredItemId);
    console.log('selected item', selectedItem.id);
    console.log('text', text);
  };
  

  return (
    <>
    {
      items.map((itemObj) => (
        <Paper style={{padding: '6px', margin:'10px', width: '350px' }}>
        <Typography variant="h6">
          {itemObj.title}
        </Typography>
        <Typography variant="body1">
          {itemObj.state}
        </Typography>
        <Typography variant="body2">
          Id: {itemObj.id}
        </Typography>


        <div style={{display: 'flex', justifyContent: 'space-between'}}>

        <Button size="small" variant="outlined" color="info" onClick={() => handleShowMoreInfoClick(itemObj)}>
          DETAILS
        </Button>

        <Button size="small" variant="contained" color="info" onClick={() => handleCreateTradeClick(itemObj)}>
          TRADE
        </Button>

        </div>

        {/* CREATE TRADE MODAL*/}  

        {selectedItem && selectedItem.id === itemObj.id && (
          <Modal
            open={showCreateTradeModal}
            onClose={handleCreateTradeClose}
          >
          <Box sx={{ position: 'absolute', top: '10%', left: '50%', transform: 'translate(-50%, -10%)', width: 400, bgcolor: 'background.paper', boxShadow: 24, p: 4 }}>
            <Typography variant="h6" gutterBottom>
              Create Trade
            </Typography>

            <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
            <section>
                <b>{itemObj.title}</b>
                <img src={itemObj.image} alt={itemObj.title} style={{width: '100%'}} />
              </section>

              <h3>TRADE FOR</h3>
              <div>OFFERED ITEM: </div>
              <Select
                labelId="demo-select-small"
                id="demo-select-small"
                value={offeredItemId}
                label="Offered item"
                onChange={(event) => setOfferedItemId(event.target.value)}
              >
                {items.map((item) => (
                  <MenuItem value={item.id}>{item.title}</MenuItem>
                ))}
              </Select>

              <br/>
              <h6>MESSAGE:</h6>
              <Textarea
                placeholder="Type in here…"
                defaultValue="..."
                minRows={6}
                maxRows={6}
                onChange={(event) => setText(event.target.value)}
                title='Comment'
              />

            </FormControl>

              <div style={{width:"100%", textAlign: "right", marginTop: '6px'}}>
                <Button size="small" variant="contained" onClick={handleCreateTradeFormSubmit}>SUBMIT</Button>
                <Button size="small" variant="contained" onClick={handleCreateTradeClose}>CANCEL</Button>
              </div>
            </Box>
          </Modal>
        )}


        {/* ITEM DETAILS MODAL */}

        {selectedItem && selectedItem.id === itemObj.id && (           
          <Modal
            open={showMoreInfoDialog}
            onClose={handleShowMoreInfoClose}
          >
            <Box sx={{ position: 'absolute', top: '10%', left: '50%', transform: 'translate(-50%, -10%)', width: 400, bgcolor: 'background.paper', boxShadow: 24, p: 4 }}>
              <img src={itemObj.image} alt={itemObj.title} style={{width: '100%'}} />
              <br/>

              <Typography variant="h6" gutterBottom>
                {selectedItem.title}
              </Typography>
              <Typography variant="body1" gutterBottom>
                {selectedItem.description}
              </Typography>
              <div style={{display:"flex", justifyContent: "space-between"}}>
              <Typography variant="body2">
                State: {selectedItem.state}
              </Typography>

              <Typography variant="body2">
                Status: {selectedItem.status}
              </Typography>

              </div>
              <Divider/>
              <div style={{width:"100%", textAlign: "right", marginTop: '6px'}}>
                <Button size="small" variant="contained" onClick={handleShowMoreInfoClose}>Close</Button>
              </div>
            </Box>
          </Modal>
        )}

      </Paper>
      ))
    }
    </>
  );
}

export default memo(ItemCard);
