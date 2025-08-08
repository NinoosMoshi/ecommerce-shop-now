package com.ninos.service.cart;

import com.ninos.dto.CartDTO;
import com.ninos.dto.UserDTO;
import com.ninos.model.Cart;
import com.ninos.model.User;

import java.math.BigDecimal;

public interface CartService {

    Cart getCartById(Long cartId);
    Cart getCartByUserId(Long userId);
    void clearCart(Long cartId);
//    Cart initializeNewCartForUser(User user);
    CartDTO initializeNewCartForUser(UserDTO userDTO);
    BigDecimal getTotalPrice(Long cartId);

}
