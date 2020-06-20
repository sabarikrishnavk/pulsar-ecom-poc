import React, { useState } from 'react'
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Axios from "axios";

const api = Axios.create({
    baseURL: 'http://localhost:4001/v1/',
});
const ManagePayment = ({ orderId }) => { 

    const[ tenderReference, setTenderReference] =useState(""); 
    const[ tenderType, setTenderType ] =useState(""); 
    const[ details,setDetails] =useState(""); 
    const[ amount ,setAmount] =useState("");  

    let onTenderReferenceChange =(e)=>{  setTenderReference(e.target.value); }
    let onTenderTypeChange =(e)=>{  setTenderType(e.target.value); }
    let onDetailshange =(e)=>{setDetails(e.target.value);  }
    let onAmountChange =(e)=>{ setAmount(e.target.value);  } 

    let managePayment=() => {
        let msg = { 
            orderId: orderId,
            tenderReference :tenderReference,
            tenderType: tenderType,
            details: details,
            amount: amount
        }
        return api.post(`payment`, msg);
    };
    return (
        <div className="message-input">
            <TextField
                className="inputField"
                label="Enter tender Reference"
                placeholder="Enter tender Reference " 
                margin="normal"
                value={tenderReference} 
                onChange={e => onTenderReferenceChange(e)}
                style={{ height: "30px", width: "80%" }}
            />
            <TextField
                className="inputField"
                label="Enter Tender Type"
                placeholder="Enter TenderType " 
                margin="normal"
                value={tenderType} 
                onChange={e => onTenderTypeChange(e)}
                style={{ height: "30px", width: "80%" }}
            />
            <TextField
                className="inputField"
                label="Enter details"
                placeholder="Enter details" 
                margin="normal"
                value={details} 
                onChange={e => onDetailshange(e)}
                style={{ height: "30px", width: "80%" }}
            />
            <TextField
                className="inputField"
                label="Enter amount"
                placeholder="Enter amount" 
                margin="normal"
                value={amount} 
                onChange={e => onAmountChange(e)}
                style={{ height: "30px", width: "80%" }}
            />

            <Button variant="contained" color="primary" onClick={managePayment}>
                Add Payment
            </Button>
        </div>
    );
}


export default ManagePayment
