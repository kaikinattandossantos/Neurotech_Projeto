package com.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "API de Produtos", version = "1.0", description = "API para gerenciamento de um cadastro de produtos.")
)
public class NeurotechApplication {
    public static void main(String[] args) {
        SpringApplication.run(NeurotechApplication.class, args);
    }
}