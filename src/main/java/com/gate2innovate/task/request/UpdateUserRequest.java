package com.gate2innovate.task.request;

import com.gate2innovate.task.model.Role;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private int id;
    private String userName;
    private String email;
    private Role role;
}
