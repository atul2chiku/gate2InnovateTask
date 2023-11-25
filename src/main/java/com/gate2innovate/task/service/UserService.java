package com.gate2innovate.task.service;

import com.gate2innovate.task.request.ChangePasswordRequest;
import com.gate2innovate.task.dto.UserDto;
import com.gate2innovate.task.request.LoginRequest;
import com.gate2innovate.task.request.RefreshTokenRequest;
import com.gate2innovate.task.request.RegisterRequest;
import com.gate2innovate.task.response.AuthenticationResponse;
import com.gate2innovate.task.response.RefreshTokenReponse;

import java.security.Principal;
import java.util.List;

public interface UserService {

    void registerUser(RegisterRequest registerRequest);

    AuthenticationResponse loginUser(LoginRequest loginRequest) throws Exception;

    RefreshTokenReponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws Exception;

    UserDto getUserById(int id);

    List<UserDto> getAllUsers();

    UserDto changePassword(ChangePasswordRequest request, Principal connectedUser);
}
