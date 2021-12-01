import React, { useState, useStyles } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';

export default function Subject(props) {

    const useStyles = makeStyles((theme) => ({
        root: {
          '& > *': {
            margin: theme.spacing(1),
          },
        },
      }));

    const [좋아요, 좋아요변경] = useState(0);
    const handleClick = ()=> {
        좋아요변경(좋아요+1);
    }
    
    const classes = useStyles();

      return(
        <header>
          <h1>React App</h1>
          <h2>{props.name} <span onClick={(handleClick)}>좋아요</span>{좋아요}</h2>
          <h2>{props.address}</h2>
          <Button variant="contained" color="primary">
                Primary
            </Button>
          <p>PhoneNumber: {props.children}</p>
          
        </header>
      ); 
   
}
