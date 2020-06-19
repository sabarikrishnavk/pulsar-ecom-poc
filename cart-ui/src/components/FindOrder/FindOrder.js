import React, { useState } from 'react'
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Axios from "axios";

const api = Axios.create({
    baseURL: 'http://localhost:4001/v1/',
});
const FindOrder = ({ onSubmit }) => {
    const [orderId, setOrderId] = useState("")
    const [order, setOrder] = useState([]) 

    let onChange = (e) => { 
        setOrderId(e.target.value);   
    }
 
    let findOrder= () => {
        console.log('Calling get order from API : '+orderId);
        api.get(`/cart/${orderId}`).then(res => {
          console.log('find order: ', res.data); 
          setOrder(res.data);
        }).catch(err => {
          console.log('Error Occured while sending message to api');
        }); 
        onSubmit(orderId,order);
      }
 
    return (
        <div className="message-input">
            <TextField
                className="inputField"
                label="Enter Order Id"
                placeholder="Enter orderId and press ENTER"
                onChange={e => onChange(e)}
                margin="normal"
                value={orderId}
                onKeyPress={event => {
                    if (event.key === 'Enter') {
                        findOrder();
                    }
                }}
                style={{ height: "30px", width: "80%" }}
            />

            <Button variant="contained" color="primary" onClick={findOrder}>
                Find Order
            </Button>
        </div>
    );
}


export default FindOrder
