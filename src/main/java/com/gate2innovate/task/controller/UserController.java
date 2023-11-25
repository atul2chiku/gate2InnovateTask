package com.gate2innovate.task.controller;

import com.gate2innovate.task.request.ChangePasswordRequest;
import com.gate2innovate.task.dto.UserDto;
import com.gate2innovate.task.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasRole(ROLE_USER)")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PatchMapping("/update-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> GetUserById(@PathVariable int id){
        UserDto user=null;
        try {
            user = userService.getUserById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(user);

    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> GetAllUsers(){
        List<UserDto> users=userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


}
