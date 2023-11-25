package com.gate2innovate.task.repository;

import com.gate2innovate.task.model.Product;
import com.gate2innovate.task.request.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product save(ProductRequest productRequest);
}
