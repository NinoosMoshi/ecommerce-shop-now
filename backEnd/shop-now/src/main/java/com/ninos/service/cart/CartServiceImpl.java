package com.ninos.service.cart;

import com.ninos.dto.CartDTO;
import com.ninos.dto.UserDTO;
import com.ninos.model.Cart;
import com.ninos.model.User;
import com.ninos.repository.CartItemRepository;
import com.ninos.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;

    @Override
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart with id: " + cartId + " not found!"));
//        BigDecimal totalAmount = cart.getTotalAmount();
//        cart.setTotalAmount(totalAmount);
//        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findCartByUserId(userId);
    }

    @Override
    public void clearCart(Long cartId) {
        Cart cart = getCartById(cartId);
        cartItemRepository.deleteAllByCartId(cartId);
        cart.clearCart();
        cartRepository.deleteById(cartId);
    }

//    @Override
//    public Cart initializeNewCartForUser(User user) {
//        return Optional.ofNullable(getCartByUserId(user.getId())).orElseGet(() -> {
//            Cart cart = new Cart();
//            cart.setUser(user);
//            return cartRepository.save(cart);
//        });
//    }

    @Override
    public CartDTO initializeNewCartForUser(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);

        Cart cart = Optional.ofNullable(getCartByUserId(user.getId())).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
        return modelMapper.map(cart, CartDTO.class);
    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        Cart cart = getCartById(cartId);
        return cart.getTotalAmount();
    }
}
