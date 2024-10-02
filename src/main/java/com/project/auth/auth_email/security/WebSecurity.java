package com.project.auth.auth_email.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {

    private final JwtEntryPoint jwtEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // CSRF korumasını devre dışı bırak
            .exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint)  // Yetkisiz erişimler için JwtEntryPoint kullan
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Session durumunu stateless yap (JWT ile çalışıyoruz)
            .and()
            .authorizeHttpRequests()  // authorizeRequests() yerine authorizeHttpRequests() kullanımı tavsiye ediliyor
                .requestMatchers("/api/v1/auth/register", "/api/v1/auth/login").permitAll()  // register ve login rotalarına izin ver
                .anyRequest().authenticated()  // diğer tüm istekler kimlik doğrulaması gerektirir
            .and()
            .httpBasic();  // Basic authentication destekle
        
        // JWT Authentication filter'ını ekle
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();  // AuthenticationManager Bean'i
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCryptPasswordEncoder Bean'i
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();  // JWT Filter Bean'i
    }
}
