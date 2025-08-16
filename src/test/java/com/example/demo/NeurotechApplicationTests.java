package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles; // Importe esta anotação

@SpringBootTest
@ActiveProfiles("test") // Diz ao Spring para carregar o application-test.properties
class NeurotechApplicationTests {

    @Test
    void contextLoads() {
        // Este teste agora vai rodar com uma configuração de banco de dados limpa e isolada.
    }
}