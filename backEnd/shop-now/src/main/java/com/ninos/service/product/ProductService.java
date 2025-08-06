package com.ninos.service.product;

import com.ninos.dto.ProductDTO;
import com.ninos.request.AddProductDTO;
import com.ninos.request.UpdateProductDTO;
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

    List<ProductDTO> getConvertedProducts(List<Product> products);
    ProductDTO convertToProductDTO(Product product);

}
