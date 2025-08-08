package com.ninos.service.cart;

import com.ninos.model.Cart;
import com.ninos.model.CartItem;
import com.ninos.model.Product;
import com.ninos.repository.CartItemRepository;
import com.ninos.repository.CartRepository;
import com.ninos.service.product.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final CartRepository cartRepository;


    //    @Override
//    public void addItemToCart(Long cartId, Long productId, int quantity) {
//        Cart cart = cartService.getCart(cartId);
//        Product product = productService.getProductById(productId);
//
//        CartItem cartItem = null;
//
//        // Look for existing cart item manually (no stream)
//        for (CartItem item : cart.getItems()) {
//            if (item.getProduct().getId().equals(productId)) {
//                cartItem = item;
//                break;
//            }
//        }
//
//        // If not found, create new CartItem
//        if (cartItem == null) {
//            cartItem = new CartItem();
//            cartItem.setCart(cart);
//            cartItem.setProduct(product);
//            cartItem.setUnitPrice(product.getPrice());
//            cartItem.setQuantity(quantity);
//        } else {
//            // If found, increase the quantity
//            cartItem.setQuantity(cartItem.getQuantity() + quantity);
//        }
//
//        // Update total price
//        cartItem.setTotalPrice();
//
//        // Add to cart (optional depending on cart logic)
//        cart.addItem(cartItem);
//
//        // Save both entities
//        cartItemRepository.save(cartItem);
//        cartRepository.save(cart);
//    }
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCartById(cartId);
        Product product = productService.getProductById(productId);

        // Check if item already exists in the cart
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());  // If it doesn’t exist, we create a new CartItem.

        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            // Update quantity (either set or add)
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        // Update total price and add item to cart
        cartItem.setTotalPrice();
        cart.addItem(cartItem);

        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCartById(cartId);
        CartItem itemToRemove = getCartItemFromCart(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }


    //    @Override
//    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
//        Cart cart = cartService.getCartById(cartId);
//        CartItem foundItem = null;
//
//        // Find the item in the cart
//        for (CartItem item : cart.getItems()) {
//            if (item.getProduct().getId().equals(productId)) {
//                foundItem = item;
//                break;
//            }
//        }
//
//        // Update item if found
//        if (foundItem != null) {
//            foundItem.setQuantity(quantity);
//            foundItem.setUnitPrice(foundItem.getProduct().getPrice());
//            foundItem.setTotalPrice();
//        }
//
//        // Recalculate the total amount of the cart
//        BigDecimal totalAmount = BigDecimal.ZERO;
//        for (CartItem item : cart.getItems()) {
//            totalAmount = totalAmount.add(item.getTotalPrice());
//        }
//
//        cart.setTotalAmount(totalAmount);
//        cartRepository.save(cart);
//    }
    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCartById(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getItems().stream().map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    //    @Override
//    public CartItem getCartItemFromCart(Long cartId, Long productId) {
//        Cart cart = cartService.getCartById(cartId);
//        for (CartItem item : cart.getItems()) {
//            if (item.getProduct().getId().equals(productId)) {
//                return item;
//            }
//        }
//        throw new EntityNotFoundException("CartItem with product ID " + productId + " not found in cart ID " + cartId);
//    }
    @Override
    public CartItem getCartItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCartById(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Cart not found!"));
    }
}
