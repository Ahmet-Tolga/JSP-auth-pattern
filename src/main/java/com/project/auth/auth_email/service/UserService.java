package com.project.auth.auth_email.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.auth.auth_email.dto.AuthResponseDto;
import com.project.auth.auth_email.dto.CreateUserDto;
import com.project.auth.auth_email.dto.LoginDto;
import com.project.auth.auth_email.entity.UserEntity;
import com.project.auth.auth_email.repository.UserRepository;
import com.project.auth.auth_email.security.JwtGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtGenerator jwtGenerator;


    public String register(CreateUserDto createUserDto){
        if(userRepository.existsByEmail(createUserDto.getEmail())){
            return "Email is taken!";
        }

        UserEntity user=new UserEntity();
        user.setUsername(createUserDto.getUsername());
        user.setEmail(createUserDto.getEmail());
        user.setRoles(createUserDto.getRoles());
        user.setPassword(this.passwordEncoder.encode(createUserDto.getPassword()));

        userRepository.save(user);

        return "User is registered successfully";
    }

    public AuthResponseDto login(LoginDto loginDto){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtGenerator.generateToken(authentication);

        return new AuthResponseDto(token);
    }

    public List<UserEntity> findAllUsers(){
        return userRepository.findAll();
    }

}
