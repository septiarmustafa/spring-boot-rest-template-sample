package com.example.demo.controllers;

import com.example.demo.constant.AppPath;
import com.example.demo.dto.CommonResponse;
import com.example.demo.dto.ProductResponse;
import com.example.demo.entities.Product;
import com.example.demo.mapper.ResponseMapper;
import com.example.demo.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = AppPath.API+AppPath.PRODUCTS, produces = "application/json")
public class ProductController {
    @Autowired
    private ProductService productService;
    private final ResponseMapper responseMapper;

    @PostMapping("/add")
    public ResponseEntity<CommonResponse<Product>> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(responseMapper.toResponse(createdProduct, null));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<ProductResponse>> getProducts() {
        ProductResponse productResponse = productService.getProducts();
       return ResponseEntity.ok(responseMapper.toResponse(productResponse, null));
    }

    @GetMapping(AppPath.ID)
    public ResponseEntity<CommonResponse<Product>> getProductById(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(responseMapper.toResponse(product, null));
    }

    @DeleteMapping(AppPath.ID)
    public ResponseEntity<CommonResponse<?>> deleteProduct(@PathVariable Integer id) {
      productService.deleteProduct(id);
        return ResponseEntity.ok(responseMapper.toSuccessResponse(null, null, "success delete product"));
    }
}
