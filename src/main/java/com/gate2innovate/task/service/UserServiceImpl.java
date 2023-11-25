package com.gate2innovate.task.service;

import com.gate2innovate.task.request.ChangePasswordRequest;
import com.gate2innovate.task.dto.UserDto;
import com.gate2innovate.task.model.*;
import com.gate2innovate.task.repository.TokenRepository;
import com.gate2innovate.task.repository.UserRepository;
import com.gate2innovate.task.request.LoginRequest;
import com.gate2innovate.task.request.RefreshTokenRequest;
import com.gate2innovate.task.request.RegisterRequest;
import com.gate2innovate.task.response.AuthenticationResponse;
import com.gate2innovate.task.response.RefreshTokenReponse;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final TokenRepository tokenRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService, TokenRepository tokenRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.tokenRepository = tokenRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerUser(RegisterRequest registerRequest) {
        User user=User.builder().userName(registerRequest.getUserName())
                        .email(registerRequest.getEmail())
                                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole()).build();
        userRepository.save(user);

    }

    @Override
    public AuthenticationResponse loginUser(LoginRequest loginRequest) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()));
        User user=userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        String jwtToken=jwtService.generateToken(user);
        RefreshToken refreshToken=refreshTokenService.createRefreshToken(user.getId());
        saveUserToken(user,jwtToken);
        revokeAllUserTokens(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();


    }

    @Override
    public RefreshTokenReponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws Exception {
        RefreshTokenReponse refreshTokenReponse=new RefreshTokenReponse();
        String token=refreshTokenRequest.getRefreshToken();
        RefreshToken refreshToken=refreshTokenService.findByToken(token);
        refreshTokenService.validateRefreshTokenExpiry(refreshToken);
        User user=refreshToken.getUser();
        String jwtToken=jwtService.generateToken(user);
        refreshTokenReponse.setToken(jwtToken);

        return refreshTokenReponse;


    }

    @Override
    public UserDto getUserById(int id) {
        User user=userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("User not found"));
        UserDto userDto=modelMapper.map(user,UserDto.class);
        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=userRepository.findAll();
        List<UserDto> userReponse=users
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return userReponse;
    }


    @Override
    public UserDto changePassword(ChangePasswordRequest request, Principal connectedUser) {

        User user=userRepository.findById(request.getId()).orElseThrow(()->new UsernameNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        UserDto response=modelMapper.map(userRepository.save(user),UserDto.class);
        return response;
    }

    private void saveUserToken(User user,String jwtToken){
        Token token=new Token();
        token.setToken(jwtToken);
        token.setExpired(false);
        token.setUser(user);
        tokenRepository.save(token);

    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
