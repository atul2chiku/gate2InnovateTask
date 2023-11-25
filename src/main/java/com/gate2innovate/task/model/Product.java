package com.gate2innovate.task.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name="Products")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false,unique = true)
    private String name;
    private String description;
    @Column(nullable = false)
    private double price;

}
