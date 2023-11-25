package com.gate2innovate.task.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;
    private String refreshToken;
    private String tokenType="Bearer ";
}
