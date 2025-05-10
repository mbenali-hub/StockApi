package com.ben3li.stockapi.servicios.impl;

import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ben3li.stockapi.servicios.AuthenticationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    @Value("${jwt.secret}")
    private String secretkey;

    private long expiraEn=860000;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    @Override
    public UserDetails autenticar(String email, String password) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );
        return userDetailsService.loadUserByUsername(email);
    }

    @Override
    public String generarToken(UserDetails userDetails) {
       Map<String,Object> claims = new HashMap<>();
       return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiraEn))
                .signWith(getClaveCodificadora(),SignatureAlgorithm.HS256)
                .addClaims(claims)
                .setSubject(userDetails.getUsername())
                .compact();          
    }


    @Override
    public UserDetails validarToken(String token) {
        Claims claims=Jwts.parserBuilder()
                            .setSigningKey(getClaveCodificadora())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        return userDetailsService.loadUserByUsername(claims.getSubject());
    }

    private Key getClaveCodificadora(){
        byte[] calveEnBytes=secretkey.getBytes();
        return Keys.hmacShaKeyFor(calveEnBytes);
    }
}
