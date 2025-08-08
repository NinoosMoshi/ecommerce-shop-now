package com.ninos.controller;

import com.ninos.model.Cart;
import com.ninos.response.ApiResponse;
import com.ninos.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @GetMapping("/user/{userId}/cart")
    public ResponseEntity<ApiResponse> getUserCart(@PathVariable Long userId) {
        Cart userCart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(new ApiResponse("Success", userCart));
    }


    @DeleteMapping("/cart/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok(new ApiResponse("clear cart successfully!", null));
    }


}
