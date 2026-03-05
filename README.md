# 💰 Digital Wallet API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/postgresql-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

## 🎯 Sobre o Projeto

Esta é uma API RESTful completa de Carteira Digital, baseada em um desafio técnico de backend. O sistema permite a criação de usuários (comuns e lojistas) e a transferência de valores entre eles, garantindo a integridade dos dados e a comunicação resiliente com serviços externos.

O foco principal deste projeto é demonstrar a aplicação de boas práticas de Engenharia de Software e o domínio do ecossistema Spring para construção de APIs robustas e escaláveis.



## 🚀 Tecnologias Utilizadas

* **Java 21:** Uso de *Records* para DTOs e recursos modernos da linguagem.
* **Spring Boot 3:** Framework base para a API REST.
* **Spring Data JPA / Hibernate:** Mapeamento objeto-relacional (ORM) e persistência de dados.
* **PostgreSQL:** Banco de dados relacional.
* **Docker & Docker Compose:** Containerização para garantir paridade entre ambientes de desenvolvimento e produção.
* **JUnit 5 & Mockito:** Testes unitários para garantir a confiabilidade das regras de negócio.
* **Springdoc OpenAPI (Swagger):** Documentação interativa e automatizada da API.
* **Jakarta Bean Validation:** Validação de dados de entrada (*Fail-Fast*).

## ⚙️ Arquitetura e Decisões Técnicas

Para ir além de um simples CRUD, a arquitetura foi desenhada para resolver problemas reais de sistemas financeiros:

1.  **Transações ACID (`@Transactional`):** A transferência de valores é uma operação atômica. Se qualquer etapa falhar (como a queda do banco de dados), o Spring realiza o *rollback* automático, impedindo inconsistências financeiras.
2.  **Resiliência em Integrações:** O sistema consulta um mock de um **Autorizador Externo** e dispara mensagens para um **Serviço de Notificação**. Foi implementado tratamento de exceções (*try-catch*) no envio de notificações para evitar que a indisponibilidade do servidor de e-mail cancele uma transação já efetivada.
3.  **Tratamento Global de Erros (Controller Advice):** Exceções internas não vazam *Stack Trace* para o cliente. Elas são interceptadas e convertidas em respostas JSON padronizadas (Status 400, 404, 500).
4.  **Isolamento de Domínio:** Uso de UUIDs como chave primária em vez de IDs sequenciais, aumentando a segurança contra enumeração de usuários. Valores financeiros são tratados exclusivamente com `BigDecimal` para evitar problemas de arredondamento em ponto flutuante.

## 🛠️ Como Executar o Projeto

**Pré-requisitos:** Você precisa ter o [Docker](https://www.docker.com/) e o [Git](https://git-scm.com/) instalados na sua máquina.

1. Clone o repositório:
```bash
git clone https://github.com/vitorbnr/wallet.git
```

2. Acesse a pasta do projeto:
```bash
cd wallet
```

3. Suba a infraestrutura (Banco de Dados) usando o Docker:
```bash
docker-compose up -d
```

4. Inicie a aplicação Spring Boot pela sua IDE ou via Maven:
```bash
./mvnw spring-boot:run
```

A API estará disponível em http://localhost:8080.

## 📚 Documentação da API
Com a aplicação rodando, acesse a interface do Swagger para explorar e testar todos os endpoints interativamente:

Acesse o Swagger UI: http://localhost:8080/swagger-ui.html


