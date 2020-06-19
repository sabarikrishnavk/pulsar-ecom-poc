import React, { useState } from 'react'
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Axios from "axios";

const api = Axios.create({
    baseURL: 'http://localhost:4001/v1/',
});
const ManageContact = ({ orderId }) => { 

    const[ firstName, setFName] =useState(""); 
    const[ lastName, setLName] =useState(""); 
    const[ email,setEmail] =useState(""); 
    const[ phone ,setPhone] =useState("");  

    let onFNameChange =(e)=>{  setFName(e.target.value); }
    let onLNameChange =(e)=>{  setLName(e.target.value); }
    let onEmailChange =(e)=>{  setEmail(e.target.value); }
    let onPhoneChange =(e)=>{  setPhone(e.target.value); } 

    let manageItem=() => {
        let msg = {
            
            orderId: orderId,
            firstName :firstName,
            lastName: lastName,
            email: email,
            phone: phone 
        }
        return api.post(`contact`, msg);
    };
    return (
        <div className="message-input">
            
            <TextField
                className="inputField"
                label="Enter first Name"
                placeholder="Enter firstName " 
                margin="normal"
                value={firstName} 
                onChange={e => onFNameChange(e)}
                style={{ height: "30px", width: "80%" }}
            />
            <TextField
                className="inputField"
                label="Enter last Name"
                placeholder="Enter lastName" 
                margin="normal"
                value={lastName} 
                onChange={e => onLNameChange(e)}
                style={{ height: "30px", width: "80%" }}
            />
            <TextField
                className="inputField"
                label="Enter email"
                placeholder="Enter email" 
                margin="normal"
                value={email} 
                onChange={e => onEmailChange(e)}
                style={{ height: "30px", width: "80%" }}
            />
            <TextField
                className="inputField"
                label="Enter phone"
                placeholder="Enter phone" 
                margin="normal"
                value={phone} 
                onChange={e => onPhoneChange(e)}
                style={{ height: "30px", width: "80%" }}
            />

            <Button variant="contained" color="primary" onClick={manageItem}>
                Add Contact
            </Button>
        </div>
    );
}


export default ManageContact
