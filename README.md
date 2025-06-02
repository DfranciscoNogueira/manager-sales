# Sistema de Compras - Java DESKTOP

## 📌 Visão Geral
Este projeto é um **sistema de vendas** desenvolvido em **Java EE Desktop**, utilizando **JPA** para persistência, **JUnit** para testes unitários e **H2** como banco de dados em memória para testes. Ele permite operações de cadastro, edição, listagem e exclusão de clientes, produtos e vendas.

## 🚀 Configuração Inicial

1️⃣ **Clone o repositório**:
```bash
git clone <URL_DO_REPOSITORIO>

2️⃣ Instale as dependências Maven:
mvn clean install


3️⃣ Configure o banco de dados:
- É necessário rodar o script SQL localizado na pasta resources para inicializar o esquema do banco.
- Caso necessário, atualize usuário e senha no arquivo persistence.xml para conectar ao banco correto.

🛠 Estrutura do Projeto

📂 src/main/java/br/com/manager
├── 📂 dto → Modelos de transferência de dados
├── 📂 model → Entidades do banco
├── 📂 repository → Camada de acesso aos dados
├── 📂 service → Regras de negócio
├── 📂 util → Utilitários
└── 📂 ui → Interface gráfica
📂 src/test/java/br/com/manager
├── 📂 service → Testes unitários das regras de negócio
└── 📂 repository → Testes unitários de persistência

Classe principal >>> Home.java 

✅ Funcionalidades Implementadas
✔ Cadastro e edição de clientes
✔ Cadastro e edição de produtos
✔ Registro de vendas com validação de limite de crédito
✔ Listagem e filtros de vendas por cliente e período
✔ Testes unitários para todas as funcionalidades

🧪 Rodando os Testes Unitários

Os testes foram desenvolvidos com JUnit 5 e H2. Para executá-los:
mvn test

👨‍💻 Autor
Projeto desenvolvido por Diego Francisco Nogueira.

© 2025
