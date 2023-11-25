package com.gate2innovate.task.response;

import lombok.Data;

@Data
public class ProductResponse {
    private int id;
    private String name;
    private String description;
    private double price;
}
