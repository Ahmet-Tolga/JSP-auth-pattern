package com.project.auth.auth_email.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.auth.auth_email.entity.UserEntity;
import com.project.auth.auth_email.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    // Bu metod, username yerine email ile çalışacak şekilde ayarlandı
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Kullanıcıyı veritabanından email ile buluyoruz
        UserEntity user = this.userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Kullanıcının rollerini SimpleGrantedAuthority'ye dönüştürüyoruz
        Collection<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role))
            .collect(Collectors.toList());

        // Email, şifre ve rolleri içeren User nesnesini döndürüyoruz
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
