package com.project.auth.auth_email.dto;

import java.util.List;

import lombok.Data;

@Data
public class CreateUserDto {
    private String username;
    private String email;
    private String password;
    private List<String> roles;
}
