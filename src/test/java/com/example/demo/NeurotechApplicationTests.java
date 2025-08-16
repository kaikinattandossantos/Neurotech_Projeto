package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Teste de integração que verifica se o contexto da aplicação Spring Boot
 * consegue ser carregado com sucesso no perfil de teste.
 */
@SpringBootTest
@ActiveProfiles("test")
class NeurotechApplicationTests {

    /**
     * Testa o carregamento do ApplicationContext. Se este teste passar,
     * significa que a configuração de beans, a conexão com o banco de dados de teste (H2)
     * e a inicialização geral da aplicação estão funcionando corretamente.
     */
    @Test
    void contextLoads() {
    }
}