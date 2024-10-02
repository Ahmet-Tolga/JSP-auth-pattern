package com.project.auth.auth_email.security;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConstants {
    public static final int JWT_EXPIRATION=70000;
    public static final String JWT_SECRET="secret-key";
}
