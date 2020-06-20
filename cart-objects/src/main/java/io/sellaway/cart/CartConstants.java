package io.sellaway.cart;

public interface CartConstants {

    String WEBHOOK_TOPIC_CART_SUMMARY   = "webhook-cart-summary" ;
    String WEBHOOK_API_CART_SUMMARY     = "/cart/summary/" ;

    String TOPIC_CART_ITEM_ADD          = "cart-item-add";
    String TOPIC_CART_ITEM_UPDATE       = "cart-item-update";
    String TOPIC_CART_ITEM_DELETE       = "cart-item-delete";

    String TOPIC_CART_LOGS              = "cart-log-stream";

    String TOPIC_CART_ADDRESS_ADD       = "cart-address-add";
    String TOPIC_CART_ADDRESS_UPDATE    = "cart-address-update";
    String TOPIC_CART_ADDRESS_DELETE    = "cart-address-delete";


    String TOPIC_CART_CONTACT_ADD       = "cart-contact-add";
    String TOPIC_CART_CONTACT_UPDATE    = "cart-contact-update";
    String TOPIC_CART_CONTACT_DELETE    = "cart-contact-delete";

    String TOPIC_CART_PAYMENT_ADD          = "cart-payment-add";
    String TOPIC_CART_PAYMENT_UPDATE       = "cart-payment-update";
    String TOPIC_CART_PAYMENT_DELETE       = "cart-payment-delete";
}
