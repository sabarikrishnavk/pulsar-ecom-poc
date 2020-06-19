import React from 'react'

const OrderSummary = ({ orderSummary }) => { 

    return (
        <> 
            <div>Order summary  </div>
            <ul className="messages-list">
                Order Id : {orderSummary.orderId} 
                <br></br>
                Number of items : {orderSummary.size}
                <br></br>
                Total Quantity : {orderSummary.totalQty}
                <br></br>
                Shipping  Charge: {orderSummary.shipping}
                
            </ul>
        </>
    )
}


export default OrderSummary