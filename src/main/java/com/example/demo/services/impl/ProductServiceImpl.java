package com.example.demo.services.impl;

import com.example.demo.constant.AppPath;
import com.example.demo.dto.ProductResponse;
import com.example.demo.entities.Product;
import com.example.demo.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate;
    String urlProduct = AppPath.BASE_URL + AppPath.PRODUCTS;

    @Override
    public Product createProduct(Product product) {
        try {
            ResponseEntity<Product> response = restTemplate.postForEntity(urlProduct+ "/add", product, Product.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to create product: " + e.getMessage(), e);
        }
    }

    @Override
    public ProductResponse getProducts() {
        try {
            ResponseEntity<ProductResponse> response = restTemplate.getForEntity(urlProduct, ProductResponse.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch products: " + e.getMessage(), e);
        }
    }

    @Override
    public Product getProductById(Integer id) {
        String url = String.format("%s/%d",urlProduct, id);
        try {
            ResponseEntity<Product> response = restTemplate.getForEntity(url, Product.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch product by id: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteProduct(Integer id) {
        String url = String.format("%s/%d",urlProduct, id);
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to delete product: " + e.getMessage(), e);
        }
    }
}
