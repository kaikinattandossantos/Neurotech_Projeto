package com.example.demo.controller;

import com.example.demo.dto.ProdutoRequestDTO;
import com.example.demo.dto.ProdutoResponseDTO;
import com.example.demo.service.Modelo_ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller REST responsável pelos endpoints de gerenciamento de produtos.
 * Define as operações de CRUD para a entidade Produto, utilizando DTOs para
 * a comunicação com o cliente e documentação via OpenAPI/Swagger.
 */
@Tag(name = "Produtos", description = "Endpoints para o gerenciamento de produtos")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final Modelo_ProdutoService produtoService;

    @Autowired
    public ProdutoController(Modelo_ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    /**
     * Cria um novo produto no sistema.
     * @param produtoRequestDTO DTO contendo os dados do produto a ser criado.
     * @return um ResponseEntity com o DTO do produto criado e o status HTTP 201 Created.
     */
    @Operation(summary = "Cria um novo produto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponseDTO.class)) }),
        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criarProduto(@Valid @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        ProdutoResponseDTO novoProduto = produtoService.save(produtoRequestDTO);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }
    
    /**
     * Busca um produto específico pelo seu ID.
     * @param id O ID do produto a ser buscado.
     * @return Um ResponseEntity com os dados do produto encontrado ou status 404 Not Found.
     */
    @Operation(summary = "Busca um produto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponseDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> obterProdutoPorId(@PathVariable Long id) {
        ProdutoResponseDTO produto = produtoService.findById(id);
        return ResponseEntity.ok(produto);
    }
    
    /**
     * Lista todos os produtos, com suporte opcional para filtro por nome e ordenação por preço.
     * @param name Filtro opcional para buscar produtos por nome (parcial, case-insensitive).
     * @param sort Critério de ordenação opcional. Valores válidos: "preco,asc" ou "preco,desc".
     * @return Um ResponseEntity com a lista de produtos encontrados.
     */
    @Operation(summary = "Lista produtos com filtros e ordenação")
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodosProdutos(
        @Parameter(description = "Filtrar produtos pelo nome (parcial, case-insensitive)") 
        @RequestParam(required = false) String name,
        
        @Parameter(description = "Ordenar por preço. Use 'preco,asc' ou 'preco,desc'") 
        @RequestParam(required = false) String sort
    ) {
        List<ProdutoResponseDTO> produtos = produtoService.findAll(name, sort);
        return ResponseEntity.ok(produtos);
    }

    /**
     * Atualiza um produto existente.
     * @param id O ID do produto a ser atualizado.
     * @param produtoDetailsDTO DTO com os dados a serem atualizados.
     * @return Um ResponseEntity com os dados do produto atualizado.
     */
    @Operation(summary = "Atualiza um produto existente")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO produtoDetailsDTO) {
        ProdutoResponseDTO produtoAtualizado = produtoService.update(id, produtoDetailsDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    /**
     * Remove um produto do sistema pelo seu ID.
     * @param id O ID do produto a ser removido.
     * @return Um ResponseEntity com status 204 No Content em caso de sucesso.
     */
    @Operation(summary = "Remove um produto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Produto removido com sucesso", content = @Content),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        produtoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}