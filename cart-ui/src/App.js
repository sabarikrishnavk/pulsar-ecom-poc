import React, { useState } from 'react';
import SockJsClient from 'react-stomp';
import './App.css'; 
import OrderSummary from './components/OrderSummary/OrderSummary';  
import FindOrder from './components/FindOrder/FindOrder';
import ManageCart from './components/ManageCart/ManageCart';
import ManageContact from './components/ManageContact/ManageContact';
import ManageAddress from './components/ManageAddress/ManageAddress';



const SOCKET_URL = 'http://localhost:4001/cart-socket/';


const App = () => {
  const [orderId,setOrderId] = useState(null)
  const [order, setOrder] = useState([]) 
  const [orderSummary, setOrderSummary] = useState([]) 

  
  let onConnected = () => {
    console.log("Connected!!")
  }

  let onMessageReceived = (msg) => {
    console.log('Order Summary update !!', msg);
    setOrderSummary(msg);
  } 
  let onFindOrder = (orderId,order) => { 
      console.log('onFindOrder:'+orderId);
      setOrderId(orderId); 
      setOrder(order);
  }

  return (
    <div className="App">
      {!!orderId ?
        (
          <>
            <SockJsClient
              url={SOCKET_URL}
              topics={['/cart/summary/'+orderId]}
              onConnect={onConnected}
              onDisconnect={console.log("Disconnected!")}
              onMessage={msg => onMessageReceived(msg)}
              debug={false}
            />
            <div> Order Items </div>
            <ManageCart orderId = {orderId} />
            <br/>
            <ManageContact orderId ={orderId}/>
            <br/>
            <ManageAddress orderId = {orderId} />
            <br/>
            <OrderSummary orderSummary={orderSummary} /> 


          </> 
        ) :
          <FindOrder onSubmit={onFindOrder}></FindOrder>
      }
    </div>
  )
}

export default App;
