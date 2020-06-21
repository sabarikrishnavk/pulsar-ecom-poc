package io.sellaway.cart;

public interface CartConstants {

    String WEBHOOK_TOPIC_CART_SUMMARY   = "webhook-carts-summary" ;
    String WEBHOOK_API_CART_SUMMARY     = "/cart/summary/" ;

    String TOPIC_CART_ITEM_ADD          = "carts-item-add";
    String TOPIC_CART_ITEM_UPDATE       = "carts-item-update";
    String TOPIC_CART_ITEM_DELETE       = "carts-item-delete";

    String TOPIC_CART_LOGS              = "carts-log-stream";

    String TOPIC_CART_ADDRESS_ADD       = "carts-address-add";
    String TOPIC_CART_ADDRESS_UPDATE    = "carts-address-update";
    String TOPIC_CART_ADDRESS_DELETE    = "carts-address-delete";


    String TOPIC_CART_CONTACT_ADD       = "carts-contact-add";
    String TOPIC_CART_CONTACT_UPDATE    = "carts-contact-update";
    String TOPIC_CART_CONTACT_DELETE    = "carts-contact-delete";

    String TOPIC_CART_PAYMENT_ADD          = "carts-payment-add";
    String TOPIC_CART_PAYMENT_UPDATE       = "carts-payment-update";
    String TOPIC_CART_PAYMENT_DELETE       = "carts-payment-delete";
}
