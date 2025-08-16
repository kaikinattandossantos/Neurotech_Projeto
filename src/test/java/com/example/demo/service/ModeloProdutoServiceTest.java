package com.example.demo.service;

import com.example.demo.dto.ProdutoRequestDTO;
import com.example.demo.models.Modelo_Produto;
import com.example.demo.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ModeloProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private Modelo_ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // aqui você pode mockar o retorno do repository e testar findAll()
    }

    @Test
    void testSave() {
        ProdutoRequestDTO dto = new ProdutoRequestDTO("Produto X", "Desc", 10.0, 5);
        Modelo_Produto produto = new Modelo_Produto(1L, "Produto X", "Desc", 10.0, 5, null);

        when(produtoRepository.save(any(Modelo_Produto.class))).thenReturn(produto);

        var response = produtoService.save(dto);

        assertEquals("Produto X", response.nome());
        verify(produtoRepository, times(1)).save(any(Modelo_Produto.class));
    }
}
