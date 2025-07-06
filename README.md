# Backend - StartupLogistica

Este README descreve a parte **backend** do projeto StartupLogistica, implementada em Spring Boot, focada em autenticação JWT e operações CRUD de usuários.

---

## Descrição do Projeto

O backend de StartupLogistica fornece uma API REST para:

* Autenticação de usuários via JWT
* Registro público de usuários
* Listagem, consulta, edição e exclusão de usuários com controle de acesso por roles
* Paginação de resultados

**Tecnologias principais**:

* Java 17
* Spring Boot (Web, Security, Data JPA, Validation)
* JWT (jjwt)
* Banco de dados H2 (teste) ou qualquer outro configurado em `application.yml`

---

## Objetivo e Escopo

* **Objetivo**: Implementar um backend seguro e testável, que sirva como referência de fluxo de autenticação e autorização em aplicações Java.
* **Escopo**:

  * Endpoints de login e registro público
  * Geração e validação de tokens JWT
  * CRUD de usuários com roles `ADMIN` e `USER`
  * Seed automático de usuário admin padrão
  * Testes de unidade e integração
  * Pipeline de CI com GitHub Actions
* **Fora de Escopo**: recursos avançados de recuperação de senha, integração com serviços externos, roles dinâmicas.

---

## Metodologia Adotada

Adotamos o framework **Kanban** no GitHub Projects:

1. **A Fazer**: backlog de stories e tarefas
2. **Em Progresso**: desenvolvimento incremental de funcionalidades
3. **Concluído**: features testadas e revisadas

Stand-ups diárias rápidas e revisões contínuas garantiram entrega incremental.

---

## Estrutura de Pacotes

```
src/main/java/br/portela/startuplogistica
├── config           # Configurações (CORS, Security, DataInitializer)
├── controllers      # REST controllers (AuthController, UserController)
├── dtos              # DTOs de input e output
├── entities         # Entidades JPA (User)
├── enums            # Enumerações (UserRole)
├── repositories     # Repositório customizado e JPA
├── security         # Filtros JWT e utilitários de token
├── usecases         # Casos de uso (CreateUser, UpdateUser, DeleteUser, etc.)
└── Application.java # Classe principal Spring Boot
```

---

## Configuração do Ambiente

1. **Banco de dados**: por padrão, usa H2 em memória para testes e desenvolvimento.

2. **Variáveis de ambiente** (em `application.yml` ou `application-*.properties`):

   ```yaml
   jwt:
     secret: "sua-chave-secreta-aqui"
     expiration-ms: 3600000  # 1 hora

   spring:
     datasource:
       url: jdbc:h2:mem:testdb
       driverClassName: org.h2.Driver
       username: sa
       password:
     jpa:
       hibernate:
         ddl-auto: update
   ```

3. **CORS**: configurado para permitir origem `http://127.0.0.1:5500` (frontend local).

---

## Execução Local

No diretório raiz do backend:

```bash
mvn spring-boot:run
```

* A aplicação estará disponível em `http://localhost:8080`
* Endpoints base: `/api/v1`

---

## Documentação da API

Se ativado, o Swagger/OpenAPI estará em:

```
http://localhost:8080/swagger-ui.html
http://localhost:8080/v3/api-docs
```

---

## Seed de Usuário Admin Padrão

Na inicialização, a classe `DataInitializer` verifica se existe o email `admin@teste.com`. Se não existir, cria:

* **Email**: `admin@teste.com`
* **Senha**: `admin123`
* **Role**: `ADMIN`

---

## Endpoints Principais

| Método | URL                  | Descrição                        |
| ------ | -------------------- | -------------------------------- |
| POST   | `/api/v1/login`      | Autenticação e geração de JWT    |
| POST   | `/api/v1/users`      | Registro público de usuário      |
| GET    | `/api/v1/user`       | Dados do usuário logado          |
| GET    | `/api/v1/users`      | Listagem paginada de usuários    |
| GET    | `/api/v1/users/{id}` | Detalhe de usuário (ADMIN only)  |
| PATCH  | `/api/v1/users/{id}` | Atualiza nome/email (ADMIN only) |
| DELETE | `/api/v1/users/{id}` | Remove usuário (ADMIN only)      |

---

## Testes

* **Unitários**: validam validações e lógica de uso de casos.
* **Integração**: utilizam `@SpringBootTest` e `MockMvc` para testar endpoints de autenticação e usuários.

Execute todos os testes:

```bash
mvn test
```

---

## Integração Contínua

Workflow em `.github/workflows/ci.yml` executa:

```bash
mvn verify
```

E publica relatórios de teste em PRs.

---

**Desenvolvido por StartupLogistica Team** 🎉
