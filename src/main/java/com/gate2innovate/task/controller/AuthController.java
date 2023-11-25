package com.gate2innovate.task.controller;


import com.gate2innovate.task.request.LoginRequest;
import com.gate2innovate.task.request.RefreshTokenRequest;
import com.gate2innovate.task.request.RegisterRequest;
import com.gate2innovate.task.response.AuthenticationResponse;
import com.gate2innovate.task.response.RefreshTokenReponse;
import com.gate2innovate.task.service.RefreshTokenService;
import com.gate2innovate.task.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(UserService userService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){

           userService.registerUser(registerRequest);
           return ResponseEntity.ok("User Registered Successfully");


    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody LoginRequest loginRequest) throws Exception {
        AuthenticationResponse authenticationResponse= userService.loginUser(loginRequest);
        return ResponseEntity.ok(authenticationResponse);

    }

    @PostMapping("/refreshToken")
    public ResponseEntity<RefreshTokenReponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws Exception {

        RefreshTokenReponse refreshTokenReponse=null;
        try {
            refreshTokenReponse = userService.refreshToken(refreshTokenRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(refreshTokenReponse);
    }


}
