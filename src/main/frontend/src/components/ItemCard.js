import React, { useState, memo } from 'react';
import { Paper, Typography, Button, Modal, Box, Divider } from '@mui/material';

const ItemCard = ({ items }) => {
  const [showMoreInfoDialog, setShowMoreInfoDialog] = useState(false);
  const [selectedItem, setSelectedItem] = useState(null);

  const handleShowMoreInfoClick = (itemObj) => {
    setSelectedItem(itemObj);
    setShowMoreInfoDialog(true);
  };

  const handleShowMoreInfoClose = () => {
    setShowMoreInfoDialog(false);
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
        <Button size="small" variant="outlined" color="error" onClick={() => handleShowMoreInfoClick(itemObj)}>
          Show More Info
        </Button>
        </div>



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
