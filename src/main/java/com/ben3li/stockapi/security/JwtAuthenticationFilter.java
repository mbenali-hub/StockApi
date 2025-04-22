package com.ben3li.stockapi.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ben3li.stockapi.servicios.impl.AuthenticationServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationServiceImpl authenticationServiceImpl;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
        
        String token= extraerToken(request);
        try {
            if(token!=null){
                UserDetails userDetails= authenticationServiceImpl.validarToken(token);

                SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities())
                );

                if(userDetails instanceof StockApiUserDetails){
                    request.setAttribute("userId", ((StockApiUserDetails)userDetails).getId());
                }
            }
        } catch (Exception e) {            
            e.printStackTrace();
        }
        
        doFilter(request, response, filterChain);
    }


    private String extraerToken(HttpServletRequest request){
        String token="";
        String bearer= request.getHeader("Authorization");

        if(bearer!=null && bearer.startsWith("Bearer ")){
            token=bearer.substring(7);
        }
        return token;
    }
    
} 