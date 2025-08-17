# API de Gerenciamento de Produtos

Uma API RESTful desenvolvida com Spring Boot para gerenciar uma base de produtos com operações CRUD completas. A aplicação está hospedada em uma instância EC2 na AWS e conectada a um banco de dados MySQL no AWS RDS, com documentação interativa via Swagger.

## URL Base da API

```
http://44.193.29.112:8080
```

⚠️ **Importante**: Para acessar a API, a porta `8080` deve estar liberada no Security Group da instância EC2. O banco de dados MySQL está hospedado no AWS RDS.

## Funcionalidades

- **CRUD de Produtos**:
  - Criar, ler, atualizar e deletar produtos.
- **Busca Avançada**: Listagem de produtos com filtro por nome (parcial, case-insensitive) e ordenação por preço (ascendente ou descendente).
- **Validação de Dados**: Entradas validadas para garantir integridade.
- **Documentação Interativa**: Endpoints documentados e testáveis via Swagger UI.

## Tecnologias Utilizadas

- **Java 17**: Linguagem principal.
- **Spring Boot 3.3.3**: Framework para a API.
- **Spring Web**: Para endpoints REST.
- **Spring Data JPA**: Para persistência de dados.
- **Maven**: Gerenciamento de dependências e build.
- **MySQL (AWS RDS)**: Banco de dados principal.
- **H2 Database**: Banco em memória para testes locais.
- **Lombok**: Redução de código boilerplate.
- **Springdoc (Swagger/OpenAPI)**: Documentação da API.

## Documentação da API

Com a aplicação rodando, acesse a documentação interativa do Swagger UI em:

```
http://44.193.29.112:8080/swagger-ui/index.html
```

### Endpoints Disponíveis

| Método   | Endpoint              | Descrição                                   |
|----------|-----------------------|---------------------------------------------|
| `POST`   | `/produtos`           | Cria um novo produto.                      |
| `GET`    | `/produtos`           | Lista todos os produtos (com filtros e ordenação). |
| `GET`    | `/produtos/{id}`      | Busca um produto por ID.                   |
| `PUT`    | `/produtos/{id}`      | Atualiza um produto existente.             |
| `DELETE` | `/produtos/{id}`      | Remove um produto por ID.                  |

## Como Executar o Projeto Localmente (Opcional)

<details>
<summary>Clique aqui para ver as instruções de execução local</summary>

### Pré-requisitos

- **JDK 17** ou superior.
- **Apache Maven 3.8** ou superior.
- **MySQL Server** local (ou Docker com imagem MySQL).

### Configuração

Crie um banco de dados MySQL local (ex.: `db_produtos`) e configure as credenciais no arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_produtos
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### Passos para Execução

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/kaikinattandossantos/Neurotech_Projeto.git
   cd Neurotech_Projeto
   ```

2. **Construa o projeto**:
   O comando abaixo compila o código, executa testes e gera o arquivo `.jar`:
   ```bash
   mvn clean install
   ```

3. **Execute a aplicação**:
   ```bash
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

   A API estará disponível em `http://localhost:8080`.

</details>

## Testes

O projeto inclui testes unitários e de integração com JUnit e Testcontainers. Execute com:
```bash
mvn test
```

Certifique-se de que o Docker está rodando para os testes com Testcontainers.

## Contribuição

1. Faça um fork do repositório.
2. Crie uma branch para sua feature: `git checkout -b minha-feature`.
3. Commit suas alterações: `git commit -m 'Adiciona minha feature'`.
4. Envie para o repositório remoto: `git push origin minha-feature`.
5. Abra um Pull Request no GitHub.

