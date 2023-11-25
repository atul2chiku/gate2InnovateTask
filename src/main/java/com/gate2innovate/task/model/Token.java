package com.gate2innovate.task.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false,unique = true)
    private String token;
    private String tokenType="Bearer";
    private boolean revoked;
    private boolean expired;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
