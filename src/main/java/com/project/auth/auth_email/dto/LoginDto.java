package com.project.auth.auth_email.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}
