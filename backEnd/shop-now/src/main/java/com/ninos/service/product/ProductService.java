package com.ninos.service.product;

import com.ninos.dto.AddProductDTO;
import com.ninos.dto.UpdateProductDTO;
import com.ninos.model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(AddProductDTO product);
    Product updateProduct(UpdateProductDTO product, Long productId);
    Product getProductById(Long productId);
    void deleteProductById(Long productId);

    List<Product> getAllProducts();
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrandAndName(String brand, String name);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByName(String name);

}
