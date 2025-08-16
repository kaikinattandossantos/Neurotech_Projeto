package com.example.demo.service;

import com.example.demo.dto.ProdutoRequestDTO;
import com.example.demo.dto.ProdutoResponseDTO;
import com.example.demo.models.Modelo_Produto;
import com.example.demo.repository.ProdutoRepository;
import com.example.demo.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


// Camada de serviço responsável pelas regras de negócio e operações da entidade Produto.

@Service
public class Modelo_ProdutoService {

    private final ProdutoRepository produtoRepository;

    /**
     * Construtor com injeção de dependência. 
     * @param produtoRepository O repositório de dados para a entidade Produto.
     */
    @Autowired
    public Modelo_ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    /**
     * Busca uma lista de produtos, com suporte a filtro por nome e ordenação.
     * @param name Filtro opcional pelo nome do produto (busca parcial, case-insensitive).
     * @param sort Critério de ordenação opcional (ex: "preco,asc" ou "preco,desc"). A ordenação padrão é por nome.
     * @return Uma lista de DTOs de resposta dos produtos encontrados.
     */
    @Transactional(readOnly = true) // Otimização: indica ao JPA que esta transação não fará alterações no banco.
    public List<ProdutoResponseDTO> findAll(String name, String sort) {
        Sort sortOrder = Sort.by("nome").ascending(); // Define uma ordenação padrão.
        
        if (sort != null) {
            if (sort.equalsIgnoreCase("preco,asc")) {
                sortOrder = Sort.by("preco").ascending();
            } else if (sort.equalsIgnoreCase("preco,desc")) {
                sortOrder = Sort.by("preco").descending();
            }
        }

        // Verifica se a lista está vazia
        List<Modelo_Produto> produtos;
        if (name != null && !name.trim().isEmpty()) {
            produtos = produtoRepository.findByNomeContainingIgnoreCase(name, sortOrder);
        } else {
            produtos = produtoRepository.findAll(sortOrder);
        }

        return produtos.stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um produto específico pelo seu ID.
     * @param id O ID do produto a ser buscado.
     * @return O DTO de resposta do produto encontrado.
     * @throws ResourceNotFoundException se nenhum produto for encontrado com o ID fornecido.
     */
    @Transactional(readOnly = true)
    public ProdutoResponseDTO findById(Long id) {
        Modelo_Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));
        return new ProdutoResponseDTO(produto);
    }

    /**
     * Cria e persiste um novo produto no banco de dados.
     * @param produtoDTO O DTO de requisição contendo os dados do novo produto.
     * @return O DTO de resposta do produto que foi salvo.
     */
    @Transactional
    public ProdutoResponseDTO save(ProdutoRequestDTO produtoDTO) {
        Modelo_Produto produto = new Modelo_Produto();
        produto.setNome(produtoDTO.nome());
        produto.setDescricao(produtoDTO.descricao());
        produto.setPreco(produtoDTO.preco());
        produto.setQuantidadeEstoque(produtoDTO.quantidadeEstoque());
        
        // A data de criação é definida pelo servidor para garantir a integridade do dado.
        produto.setDataCriacao(LocalDateTime.now());

        Modelo_Produto produtoSalvo = produtoRepository.save(produto);
        return new ProdutoResponseDTO(produtoSalvo);
    }

    /**
     * Atualiza um produto existente com base nos dados fornecidos.
     * Apenas os campos não nulos do DTO de requisição serão atualizados.
     * @param id O ID do produto a ser atualizado.
     * @param produtoDetailsDTO O DTO com os novos dados do produto.
     * @return O DTO de resposta do produto atualizado.
     * @throws ResourceNotFoundException se o produto não for encontrado.
     */
    @Transactional
    public ProdutoResponseDTO update(Long id, ProdutoRequestDTO produtoDetailsDTO) {
        Modelo_Produto existingProduto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado para atualização com o ID: " + id));

        // Lógica de atualização parcial: só atualiza os campos que não forem nulos no DTO.
        // Isso permite que o cliente envie apenas os campos que deseja alterar.
        if (produtoDetailsDTO.nome() != null) {
            existingProduto.setNome(produtoDetailsDTO.nome());
        }
        if (produtoDetailsDTO.descricao() != null) {
            existingProduto.setDescricao(produtoDetailsDTO.descricao());
        }
        if (produtoDetailsDTO.preco() != null) {
            existingProduto.setPreco(produtoDetailsDTO.preco());
        }
        if (produtoDetailsDTO.quantidadeEstoque() != null) {
            existingProduto.setQuantidadeEstoque(produtoDetailsDTO.quantidadeEstoque());
        }

        Modelo_Produto produtoAtualizado = produtoRepository.save(existingProduto);
        return new ProdutoResponseDTO(produtoAtualizado);
    }

    /**
     * @param id O ID do produto a ser deletado.
     * @throws ResourceNotFoundException se o produto não for encontrado, garantindo um feedback claro ao cliente.
     */
    @Transactional
    public void deleteById(Long id) {
        // Verifica se o produto existe antes de tentar deletar para fornecer uma mensagem de erro mais clara.
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado para remoção com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
}