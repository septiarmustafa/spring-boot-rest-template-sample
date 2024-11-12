package com.example.demo.services;

import com.example.demo.dto.ProductResponse;
import com.example.demo.entities.Product;

public interface ProductService {
    Product createProduct(Product product);
    ProductResponse getProducts();
    Product getProductById(Integer id);
    void deleteProduct(Integer id);

}
