package com.project.auth.auth_email.security;

import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtGenerator {

    @Autowired
    private SecurityConstants securityConstants;

    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + securityConstants.JWT_EXPIRATION);

        JwtBuilder jwtBuilder = Jwts.builder()
            .setSubject(email)
            .setIssuedAt(currentDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, securityConstants.JWT_SECRET);

        return jwtBuilder.compact(); 
    }

    public String getUsernameFromJwt(String token){
        Claims claims=Jwts.parser().setSigningKey(securityConstants.JWT_SECRET).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(securityConstants.JWT_SECRET).parseClaimsJws(token);
            return true;
        }
        catch(Exception err){
            throw new AuthenticationCredentialsNotFoundException(token);
        }
    }
}
