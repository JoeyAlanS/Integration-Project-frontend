# Projeto de Integração 🔧🔄

Projeto de Integração de Novos Colaboradores, uma aplicação JavaFX projetada para auxiliar na integração e treinamento de novos membros da equipe de desenvolvimento. Este repositório documenta a evolução do projeto através de fases organizadas e progressivas.

## ✨ Funcionalidades Principais

### Projeto 01: Configuração Inicial e Primeira Aplicação JavaFX
- Configuração do ambiente de desenvolvimento Java.
- Introdução a JavaFX e Git Flow.
- Geração de uma aplicação básica com interface JavaFX.

### Projeto 02: Separando Frontend e Backend
- Reestruturação para separar frontend e backend.
- Frontend ajustado para consumir dados do backend.

### Projeto 03: Conexão com Banco de Dados e Geração de Instalador
- Integração do backend com PostgreSQL usando Hibernate.
- Geração de instaladores para distribuição.

### Projeto 04: API REST e Integração Contínua
- Criação de uma API REST com Spring Boot e Swagger.
- Configuração de integração contínua com TeamCity.

### Projeto 05: Testes Unitários
- Implementação de testes unitários com JUnit e Mockito.
- Geração de relatórios de cobertura de código.

## 🛠️ Tecnologias Utilizadas

- **Backend**: Java, Hibernate, Spring Boot
- **Frontend**: JavaFX, FXML
- **Banco de Dados**: PostgreSQL
- **Outras Ferramentas**:
  - Git, Git Flow
  - TeamCity, Swagger
  - JUnit, Mockito

## 🔧 Configuração do Ambiente

### Pré-requisitos

- **JDK**: Java 8 (32 e 64 bits).
- **IDE**: IntelliJ com extensões JavaFX.
- **Banco de Dados**: PostgreSQL configurado.
- **Ferramentas de Integração**: TeamCity configurado para build e deploy.

### Instruções de Uso

1. Clone os repositórios de frontend e backend.
    ```bash
    git clone https://github.com/usuario/nome-do-repositorio-frontend.git
    git clone https://github.com/usuario/nome-do-repositorio-backend.git
    ```

2. Configure o banco de dados conforme especificações do Projeto 03.

3. Execute os testes e builds utilizando os scripts fornecidos.

4. Gere o instalador através do TeamCity ou manualmente.

## 📂 Estrutura do Projeto

### Frontend:
- Consome dados do backend via API REST.
- Interface desenvolvida com JavaFX e FXML.

### Backend:
- Provedor de dados e serviços REST.
- Configurado para integração com PostgreSQL.

### Banco de Dados:
- Estrutura baseada na "Tabela de Dados" especificada no Projeto 01.

## 🧑‍💻 Contribuidores

- **Nome do autor**: Joey Alan
- **Contato**: joeyalandev@gmail.com
