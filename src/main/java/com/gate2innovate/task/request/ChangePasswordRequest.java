package com.gate2innovate.task.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private int id;
    private String password;
}
