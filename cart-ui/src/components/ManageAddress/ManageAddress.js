import React, { useState } from 'react'
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Axios from "axios";

const api = Axios.create({
    baseURL: 'http://localhost:4001/v1/',
});
const ManageAddress = ({ orderId }) => { 

    const[ name, setName] =useState(""); 
    const[ address, setAddress] =useState(""); 
    const[ zipCode,setZipCode] =useState(""); 
    const[ state ,setStateName] =useState(""); 
    const[ addressType ,setAddressType] =useState(""); 

    let onNameChange =(e)=>{  setName(e.target.value); }
    let onAddressChange =(e)=>{  setAddress(e.target.value); }
    let onZipCodeChange =(e)=>{setZipCode(e.target.value);  }
    let onStateChange =(e)=>{ setStateName(e.target.value);  }
    let onAddressTypeChange =(e)=>{ setAddressType(e.target.value);  }

    let manageItem=() => {
        let msg = {
            
            orderId: orderId,
            name :name,
            address: address,
            zipCode: zipCode,
            state: state,
            addressType: addressType
        }
        return api.post(`address`, msg);
    };
    return (
        <div className="message-input">
            <TextField
                className="inputField"
                label="Enter store name"
                placeholder="Enter name " 
                margin="normal"
                value={name} 
                onChange={e => onNameChange(e)}
                style={{ height: "30px", width: "80%" }}
            />
            <TextField
                className="inputField"
                label="Enter address"
                placeholder="Enter address " 
                margin="normal"
                value={address} 
                onChange={e => onAddressChange(e)}
                style={{ height: "30px", width: "80%" }}
            />
            <TextField
                className="inputField"
                label="Enter zipCode"
                placeholder="Enter zipCode" 
                margin="normal"
                value={zipCode} 
                onChange={e => onZipCodeChange(e)}
                style={{ height: "30px", width: "80%" }}
            />
            <TextField
                className="inputField"
                label="Enter state"
                placeholder="Enter state" 
                margin="normal"
                value={state} 
                onChange={e => onStateChange(e)}
                style={{ height: "30px", width: "80%" }}
            />
            <TextField
                className="inputField"
                label="Enter type"
                placeholder="Enter type (BILLING or SHIPPING )" 
                margin="normal"
                value={addressType} 
                onChange={e => onAddressTypeChange(e)}
                style={{ height: "30px", width: "80%" }}
            />

            <Button variant="contained" color="primary" onClick={manageItem}>
                Add Address
            </Button>
        </div>
    );
}


export default ManageAddress
