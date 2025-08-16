package com.example.demo.repository;

import com.example.demo.models.Modelo_Produto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface de repositório para a entidade {@link Modelo_Produto}.
 * Utiliza o Spring Data JPA para fornecer operações de CRUD e consultas customizadas.
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Modelo_Produto, Long> {

    /**
     * Busca produtos cujo nome contenha a string fornecida, ignorando capitalização.
     * A consulta é gerada automaticamente pelo Spring Data JPA com base na assinatura do método.
     *
     * @param nome O termo de busca a ser encontrado no nome dos produtos.
     * @param sort O critério de ordenação para os resultados.
     * @return Uma lista de produtos que correspondem aos critérios de busca e ordenação.
     */
    List<Modelo_Produto> findByNomeContainingIgnoreCase(String nome, Sort sort);

}