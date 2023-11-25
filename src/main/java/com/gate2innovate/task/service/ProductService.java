package com.gate2innovate.task.service;

import com.gate2innovate.task.request.ProductRequest;
import com.gate2innovate.task.response.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(int productId) throws Exception;

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(int productId, ProductRequest updatedProductRequest) throws Exception;

    void deleteProduct(int productId);
}
