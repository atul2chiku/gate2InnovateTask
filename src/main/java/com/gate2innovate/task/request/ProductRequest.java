package com.gate2innovate.task.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ProductRequest {
    private int id;
    private String name;
    private String description;
    private double price;
}
