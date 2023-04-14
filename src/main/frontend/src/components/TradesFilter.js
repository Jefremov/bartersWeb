import React from 'react';
import { Button } from '@mui/material';
import Done from '@mui/icons-material/Done';
import Clear from '@mui/icons-material/Clear';
import AccessTimeIcon from '@mui/icons-material/AccessTime';

const TradeFilter = ({ handleFilter }) => {
  return (
    <section style={{textAlign: 'center'}}>
      <Button 
      variant="outlined" 
      onClick={() => handleFilter('PENDING')}
      startIcon={<AccessTimeIcon />}
      style={{margin: '4px'}}
      >
        PENDING
      </Button>
      <Button 
      variant="outlined" 
      onClick={() => handleFilter('ACCEPTED')}
      startIcon={<Done />}
      style={{margin: '4px'}}
      >
        ACCEPTED 
      </Button>
      <Button 
      variant="outlined" 
      onClick={() => handleFilter('DECLINED')}
      startIcon={<Clear />}
      style={{margin: '4px'}}
      >
        DECLINED 
      </Button>
    </section>
  );
};

export default TradeFilter;
