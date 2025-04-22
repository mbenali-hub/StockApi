package com.ben3li.stockapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ben3li.stockapi.repositorios.UsuarioRepositorio;
import com.ben3li.stockapi.security.JwtAuthenticationFilter;
import com.ben3li.stockapi.security.StockApiUserDetails;
import com.ben3li.stockapi.security.StockApiUserDetailsServirce;
import com.ben3li.stockapi.servicios.impl.AuthenticationServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception{
        http.authorizeHttpRequests(auth-> auth 
            .requestMatchers(HttpMethod.POST, "/stockapi/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/stockapi/registro").permitAll()
            .requestMatchers(HttpMethod.POST,"/stockapi/crearubicacion").permitAll()
            .anyRequest().authenticated()
        )
        .csrf(csrf->csrf.disable())
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean 
    public UserDetailsService userDetailsService(UsuarioRepositorio usuarioRepositorio){
        return new StockApiUserDetailsServirce(usuarioRepositorio);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationServiceImpl authenticationServiceImpl){
        return new JwtAuthenticationFilter(authenticationServiceImpl);
    }

}
