# API REST de Cadastro e controle de uma Livraria

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)


## Descrição

Esta é uma API RESTful desenvolvida com **Spring Boot** e **Java 24**, que serve como backend para o cadastro e gerenciamento de clientes de uma livraria. A API oferece funcionalidades para autenticação, cadastro de usuários e operações CRUD (Create, Read, Update, Delete) para o gerenciamento de clientes.

Além disso, a API gera um token de autenticação JWT (JSON Web Token), que é válido por 12 horas, sendo recomendado armazená-lo de forma segura.

## Tecnologias Utilizadas

- **Java 23**
- **Spring Boot 3.4.2**
- **Spring Security** (para autenticação e autorização)
- **JPA (Java Persistence API)** com **PostgreSQL** para persistência de dados
- **Swagger OpenAPI** para documentação da API
- **JWT (JSON Web Token)** para autenticação
- **Maven** para gerenciamento de dependências

# Como Executar

1. Clone o repositório.
2. Configure a base de dados PostgreSQL.
3. No arquivo `application.properties`, configure os dados da conexão com o banco de dados.
4. Execute a aplicação com o comando:

   ```bash
   mvn spring-boot:run
   
5. Acesse a API pela URL: http://localhost:8081 (ou a URL configurada).
