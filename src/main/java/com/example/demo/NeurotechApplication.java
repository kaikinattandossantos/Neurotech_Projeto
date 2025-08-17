package com.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

// Importe as classes que faltavam
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

/**
 * Classe principal que serve como ponto de entrada para a aplicação Spring Boot.
 * Contém também a configuração geral da documentação OpenAPI.
 */
@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "API de Produtos", version = "1.0", description = "API para gerenciamento de um cadastro de produtos.")
)
public class NeurotechApplication {

    // 1. Os campos @Autowired devem ficar aqui, no nível da classe.
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(NeurotechApplication.class, args);
    }

    // 2. O método init() também deve ficar aqui, fora do main.
    @PostConstruct
    public void init() {
        // Verifica se o usuário já existe para não criar duplicado
        if (userRepository.findByUsername("admin") == null) {
            String encodedPassword = passwordEncoder.encode("password");
            User adminUser = new User("admin", encodedPassword);
            userRepository.save(adminUser);
            System.out.println(">>> Usuário 'admin' criado com a senha 'password' <<<");
        }
    }
}