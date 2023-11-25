package com.gate2innovate.task.service;

import com.gate2innovate.task.model.RefreshToken;
import com.gate2innovate.task.model.User;
import com.gate2innovate.task.repository.RefreshTokenRepository;
import com.gate2innovate.task.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{
    private static final long refreshTokenExpiration=604800000;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    @Override
    public RefreshToken createRefreshToken(int userId){
        RefreshToken refreshToken=new RefreshToken();
        User user=userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshTokenRepository.save(refreshToken);

        return refreshToken;


    }

    public RefreshToken validateRefreshTokenExpiry(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(refreshToken);
        }
        return refreshToken;
    }

    public RefreshToken findByToken(String refreshToken) throws Exception {
        RefreshToken refreshTokenReponse=refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(()-> new Exception("RefreshToken not found"));

        return refreshTokenReponse;
    }


}
