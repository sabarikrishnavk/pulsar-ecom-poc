import React, { useState } from 'react'
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Axios from "axios";

const api = Axios.create({
    baseURL: 'http://localhost:4001/v1/',
});
const ManageCart = ({ orderId }) => { 

    const[ sku, setSku] =useState(""); 
    const[ quantity,setQuantity] =useState(""); 
    const[ shippingMethod ,setShipMethod] =useState(""); 

    let onSkuChange =(e)=>{
        setSku(e.target.value);  
    }
    let onQuantityChange =(e)=>{
        setQuantity(e.target.value);  
    }
    let onShipMethodChange =(e)=>{
        setShipMethod(e.target.value);  
    }
    let manageItem=() => {
        let msg = {
            orderId: orderId,
            sku: sku,
            quantity: quantity,
            shippingMethod: shippingMethod
        }
        return api.post(`item`, msg);
    };
    return (
        <div className="message-input">
            <TextField
                className="inputField"
                label="Enter sku"
                placeholder="Enter sku " 
                margin="normal"
                value={sku} 
                onChange={e => onSkuChange(e)}
                style={{ height: "30px", width: "80%" }}
            />
            <TextField
                className="inputField"
                label="Enter quantity"
                placeholder="Enter quantity" 
                margin="normal"
                value={quantity} 
                onChange={e => onQuantityChange(e)}
                style={{ height: "30px", width: "80%" }}
            />
            <TextField
                className="inputField"
                label="Enter shippingMethod"
                placeholder="Enter shippingMethod" 
                margin="normal"
                value={shippingMethod} 
                onChange={e => onShipMethodChange(e)}
                style={{ height: "30px", width: "80%" }}
            />

            <Button variant="contained" color="primary" onClick={manageItem}>
                Add Item
            </Button>
        </div>
    );
}


export default ManageCart
