package com.example.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Desabilita CSRF pois a API é stateless
                .csrf(csrf -> csrf.disable())
                // Configura a sessão como stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configura as regras de autorização para os endpoints
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso público ao endpoint de login
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        // Permite acesso público à documentação do Swagger
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // Qualquer outra requisição precisa de autenticação
                        .anyRequest().authenticated()
                )
                // Adiciona nosso filtro de JWT antes do filtro padrão do Spring
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Bean que expõe o AuthenticationManager, necessário para o processo de autenticação.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Bean que define o algoritmo de criptografia de senhas.
     * Usamos o BCrypt, que é o padrão recomendado.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}