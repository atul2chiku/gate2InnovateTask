package com.gate2innovate.task.service;

import com.gate2innovate.task.model.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(int userId);
    RefreshToken findByToken(String token) throws Exception;
    RefreshToken validateRefreshTokenExpiry(RefreshToken refreshToken);
}
