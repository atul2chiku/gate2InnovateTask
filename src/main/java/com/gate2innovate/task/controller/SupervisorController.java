package com.gate2innovate.task.controller;

import com.gate2innovate.task.dto.UserDto;
import com.gate2innovate.task.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/supervisor")
@PreAuthorize("hasRole(ROLE_SUPERVISOR)")
public class SupervisorController {

    private final UserService userService;

    public SupervisorController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> GetUserById(@PathVariable int id){
        UserDto user=userService.getUserById(id);
        return ResponseEntity.ok(user);

    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> GetAllUsers(){
        List<UserDto> users=userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}
