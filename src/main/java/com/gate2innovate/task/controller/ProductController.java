package com.gate2innovate.task.controller;


import com.gate2innovate.task.request.ProductRequest;
import com.gate2innovate.task.response.ProductResponse;
import com.gate2innovate.task.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    @PreAuthorize("hasRole(USER_ROLE)")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> productResponse=productService.getAllProducts();
        return  ResponseEntity.ok(productResponse);
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasRole(USER_ROLE)")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int productId) throws Exception {
        ProductResponse productResponse=productService.getProductById(productId);
        return ResponseEntity.ok(productResponse);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole(USER_SUPERVISOR)")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse=productService.createProduct(productRequest);
        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/update/{productId}")
    @PreAuthorize("hasRole(USER_SUPERVISOR)")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable int productId, @RequestBody ProductRequest updatedProductRequest
    ) throws Exception {
        ProductResponse productResponse= productService.updateProduct(productId, updatedProductRequest);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasRole(USER_SUPERVISOR)")
    public void deleteProduct(@PathVariable int productId) {
        productService.deleteProduct(productId);
    }
}