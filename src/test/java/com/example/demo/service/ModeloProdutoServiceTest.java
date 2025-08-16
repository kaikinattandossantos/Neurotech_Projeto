package com.example.demo.service;

import com.example.demo.dto.ProdutoRequestDTO;
import com.example.demo.dto.ProdutoResponseDTO;
import com.example.demo.models.Modelo_Produto;
import com.example.demo.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe {@link Modelo_ProdutoService}.
 * Utiliza Mockito para simular o comportamento do repositório e isolar a camada de serviço.
 */
class ModeloProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private Modelo_ProdutoService produtoService;

    /**
     * Configura o ambiente de mock do Mockito antes de cada teste ser executado.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * TODO: Implementar teste para o método findAll, cobrindo os cenários
     * de busca com e sem filtro por nome e com diferentes ordenações.
     */
    @Test
    void testFindAll() {
        // Cenário, execução e asserções a serem implementadas.
    }

    /**
     * Testa se o serviço salva um novo produto corretamente.
     */
    @Test
    void testSave() {
        // Given (Dado)
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Produto X", "Desc", 10.0, 5);
        Modelo_Produto produtoSalvo = new Modelo_Produto(1L, "Produto X", "Desc", 10.0, 5, null);
        when(produtoRepository.save(any(Modelo_Produto.class))).thenReturn(produtoSalvo);

        // When (Quando)
        ProdutoResponseDTO response = produtoService.save(dto);

        // Then (Então)
        assertNotNull(response);
        assertEquals("Produto X", response.nome());
        verify(produtoRepository, times(1)).save(any(Modelo_Produto.class));
    }
}