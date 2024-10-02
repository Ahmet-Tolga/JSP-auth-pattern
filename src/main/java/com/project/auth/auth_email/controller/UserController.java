package com.project.auth.auth_email.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.auth.auth_email.dto.AuthResponseDto;
import com.project.auth.auth_email.dto.CreateUserDto;
import com.project.auth.auth_email.dto.LoginDto;
import com.project.auth.auth_email.entity.UserEntity;
import com.project.auth.auth_email.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    private ResponseEntity<String> register(@RequestBody CreateUserDto createUserDto){
        return ResponseEntity.ok(userService.register(createUserDto));
    }

    @PostMapping("/login")
    private ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @GetMapping("/findAll")
    private ResponseEntity<List<UserEntity>> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }
}
