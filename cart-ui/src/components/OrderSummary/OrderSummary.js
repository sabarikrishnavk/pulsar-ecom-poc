import React from 'react'

const OrderSummary = ({ orderSummary }) => { 

    return (
        <> 
            <div>Order summary  </div>
            <ul className="messages-list">
                Order Id : {orderSummary.orderId} 
                <br></br>
                Number of items : {orderSummary.lines}
                <br></br>
                Total Quantity : {orderSummary.quantity}
                <br></br>
                Shipping  Charge: {orderSummary.shipping}
                <br></br>
                Shipping  Charge: {orderSummary.shipping}
                
            </ul>
        </>
    )
}


export default OrderSummary