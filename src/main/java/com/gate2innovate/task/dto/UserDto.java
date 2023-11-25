package com.gate2innovate.task.dto;

import com.gate2innovate.task.model.Role;
import com.gate2innovate.task.model.Token;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private int id;
    private String userName;
    private String email;
    private String password;
    private Role role;
}
