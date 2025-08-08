package com.ninos.service.cart;

import com.ninos.model.CartItem;

public interface CartItemService {

    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);
    CartItem getCartItemFromCart(Long cartId, Long productId);

}
