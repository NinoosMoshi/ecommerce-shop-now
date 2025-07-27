package com.ninos.service.product;

import com.ninos.request.AddProductDTO;
import com.ninos.request.UpdateProductDTO;
import com.ninos.model.*;
import com.ninos.repository.CartItemRepository;
import com.ninos.repository.CategoryRepository;
import com.ninos.repository.OrderItemRepository;
import com.ninos.repository.ProductRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;


//    @Override
//    public Product addProduct(AddProductDTO productDTO) {
//        if (productExists(productDTO.getName(), productDTO.getBrand())) {
//            throw new EntityExistsException(productDTO.getName() + " already exists!");
//        }
//
//        Category category = categoryRepository.findByName(productDTO.getCategory().getName());
//        if (category == null) {
//            category = new Category(productDTO.getCategory().getName());
//            category = categoryRepository.save(category);
//        }
//
//        productDTO.setCategory(category);
//        return productRepository.save(createProduct(productDTO, category));
//    }
    @Override
    public Product addProduct(AddProductDTO productDTO) {
        if (productExists(productDTO.getName(), productDTO.getBrand())) {
            throw new EntityExistsException(productDTO.getName() + " already exists!");
        }
        Category category = Optional.ofNullable(categoryRepository.findByName(productDTO.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(productDTO.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        productDTO.setCategory(category);
        return productRepository.save(createProduct(productDTO, category));
    }


//    @Override
//    public Product updateProduct(UpdateProductDTO productDTO, Long productId) {
//        Product existingProduct = productRepository.findById(productId)
//                .orElseThrow(() -> new EntityNotFoundException("Product not found!"));
//
//        updateExistingProduct(existingProduct, productDTO);
//        return productRepository.save(existingProduct);
//    }
    @Override
    public Product updateProduct(UpdateProductDTO productDTO, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, productDTO))
                .map(productRepository :: save)
                .orElseThrow(() -> new EntityNotFoundException("Product not found!"));
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

    }


//    @Override
//    public void deleteProductById(Long productId) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new EntityNotFoundException("Product not found!"));
//
//        // Remove product from cart items
//        List<CartItem> cartItems = cartItemRepository.findByProductId(productId);
//        for (CartItem cartItem : cartItems) {
//            Cart cart = cartItem.getCart();
//            cart.removeItem(cartItem);
//            cartItemRepository.delete(cartItem);
//        }
//
//        // Remove product reference from order items
//        List<OrderItem> orderItems = orderItemRepository.findByProductId(productId);
//        for (OrderItem orderItem : orderItems) {
//            orderItem.setProduct(null);
//            orderItemRepository.save(orderItem);
//        }
//
//        // Remove product from its category
//        Category category = product.getCategory();
//        if (category != null) {
//            category.getProducts().remove(product);
//            product.setCategory(null);
//        }
//
//        // Delete product
//        productRepository.deleteById(product.getId());
//    }
    @Override
    public void deleteProductById(Long productId) {
        productRepository.findById(productId)
                .ifPresentOrElse(product -> {
                    List<CartItem> cartItems = cartItemRepository.findByProductId(productId);
                    cartItems.forEach(cartItem -> {
                        Cart cart = cartItem.getCart();
                        cart.removeItem(cartItem);
                        cartItemRepository.delete(cartItem);
                    });

                    List<OrderItem> orderItems = orderItemRepository.findByProductId(productId);
                    orderItems.forEach(orderItem -> {
                        orderItem.setProduct(null);
                        orderItemRepository.save(orderItem);
                    });

                    Optional.ofNullable(product.getCategory())
                            .ifPresent(category -> category.getProducts().remove(product));
                    product.setCategory(null);

                    productRepository.deleteById(product.getId());

                }, () -> {
                    throw new EntityNotFoundException("Product not found!");
                });
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findProductsByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findProductsByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findProductsByBrandAndName(brand, name);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findProductsByBrand(brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findProductsByName(name);
    }


    private boolean productExists(String name, String brand) {
        return productRepository.existsByNameAndBrand(name, brand);
    }

    private Product createProduct(AddProductDTO addProductDTO, Category category) {
        return new Product(
                addProductDTO.getName(),
                addProductDTO.getBrand(),
                addProductDTO.getPrice(),
                addProductDTO.getInventory(),
                addProductDTO.getDescription(),
                category
        );
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductDTO productDTO) {
        existingProduct.setName(productDTO.getName());
        existingProduct.setBrand(productDTO.getBrand());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setInventory(productDTO.getInventory());
        Category category = categoryRepository.findByName(productDTO.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }


}
