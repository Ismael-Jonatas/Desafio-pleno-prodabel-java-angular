# Desafio Técnico – Nível Pleno  
**Empresa: PRODABEL**  

Sistema de gestão de atendimentos municipais, com backend em **Spring Boot** e frontend em **Angular**.  
Permite abertura de solicitações por cidadãos, atribuição a funcionários e visualização de métricas de atendimento por bairro.

---

## Tecnologias

- **Backend:** Java 17, Spring Boot 3, Spring Data JPA, Bean Validation, OpenAPI/Swagger  
- **Frontend:** Angular 17, TypeScript, RxJS, Ngx-Charts (ou Chart.js)  
- **Banco de Dados:** PostgreSQL (via Docker)  
- **Build:** Maven, Node.js  
- **CI/CD:** GitHub Actions  

---

## Estrutura do Projeto

desafio-pleno-prodabel-java-angular/
│
├── backend/ # Spring Boot (API REST)
│ ├── src/main/java/com/prodabel/...
│ └── pom.xml
│
├── frontend/ # Angular
│ ├── src/app/...
│ └── package.json
│
├── docker-compose.yml
├── .gitignore
└── README.md


---

## Como rodar o projeto localmente

### Pré-requisitos
- [Java 17+](https://adoptium.net/)  
- [Maven 3.9+](https://maven.apache.org/)  
- [Node.js 20+](https://nodejs.org/)  
- [Angular CLI](https://angular.dev/tools/cli)  
- [Docker](https://docs.docker.com/get-docker/)  

---

## Banco estará acessível em:

Host: localhost

Porta: 5432

Usuário: prodabel

Senha: prodabel123

Database: desafio

