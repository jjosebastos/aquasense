---

## AquaSense: Monitoramento e Análise de Áreas de Risco

---

### Visão Geral

O **AquaSense** é uma solução abrangente projetada para monitorar e analisar áreas de risco na cidade de São Paulo, focando em eventos críticos como **alagamentos**, **desmoronamentos** e problemas de **trânsito**. Através da combinação de inteligência artificial e um robusto backend, o sistema extrai e consolida informações vitais de diversas fontes. Isso permite a apresentação de dados estruturados que são cruciais para a tomada de decisões rápidas e a implementação de ações preventivas.

---

### Tecnologias Essenciais

O AquaSense é construído sobre um stack tecnológico moderno e eficiente, combinando as capacidades do Spring Boot para o backend com a inteligência artificial do Google Gemini para análise de dados e a integração com a OpenWeather API para informações climáticas.

#### Backend (Java com Spring Boot)

* **Java 17+:** A linguagem de programação principal, escolhida por sua performance, estabilidade e amplo suporte da comunidade.
* **Spring Boot 3.x:** Um framework que simplifica o desenvolvimento e deploy de aplicações Java, provendo uma base sólida para microsserviços.
    * **Spring Data JPA:** Facilita a interação com bancos de dados relacionais através da especificação JPA.
    * **Lombok:** Reduz a verbosidade do código Java, automatizando a criação de getters, setters e construtores.
    * **WebClient (Spring WebFlux):** Cliente HTTP reativo essencial para a comunicação assíncrona com APIs externas.
    * **Scheduled Tasks:** Funcionalidade para agendamento de tarefas em segundo plano, como a limpeza de dados e atualização de informações climáticas.
    * **Logback (SLF4J):** Um framework de logging eficaz para monitoramento e depuração da aplicação.
* **Hibernate 6.x:** A implementação ORM (Object-Relational Mapping) do JPA, responsável por mapear objetos Java para o banco de dados.
* **Jackson:** Biblioteca fundamental para a serialização e desserialização de dados JSON, crucial para a comunicação com APIs externas e a própria API REST da aplicação.
* **MySQL Connector/J:** O driver JDBC que permite a conexão entre o Spring Boot e o banco de dados MySQL.
* **Maven:** A ferramenta padrão de automação de build para gerenciar dependências e o ciclo de vida do projeto.

#### Inteligência Artificial e Dados Externos

* **Google Gemini API:** Utilizada para processamento de linguagem natural (NLP). Extrai insights e informações críticas de textos não estruturados (como notícias e redes sociais) para identificar e categorizar áreas de risco. Atua como um "agente de análise de riscos" virtual.
* **OpenWeather API:** Fornece dados climáticos em tempo real, incluindo temperatura, para as coordenadas geográficas das áreas monitoradas. Essa integração enriquece a análise de riscos com informações contextuais sobre as condições meteorológicas.

#### Banco de Dados

* **MySQL:** Escolhido como o sistema de gerenciamento de banco de dados relacional para persistência, integridade e escalabilidade dos dados do AquaSense.

---

### Estrutura do Projeto

O AquaSense adota uma arquitetura em camadas, com DTOs (Data Transfer Objects) que asseguram uma comunicação clara e eficiente entre as diferentes partes da aplicação e com as APIs externas:

* **`model/`:** Contém as **entidades JPA** (`AreaRisco`), que representam diretamente a estrutura das tabelas no banco de dados.
* **`model/dto/`:** Classes **DTO** (`AreaRiscoData`, `AreaRiscoRequest`, `AreaRiscoResponse`, e DTOs específicos da OpenWeather API) para gerenciar o fluxo de dados de entrada e saída.
* **`repository/`:** Interfaces de **repositório** (`AreaRiscoRepository`) que estendem o Spring Data JPA, simplificando as operações de persistência e consulta.
* **`service/`:** A camada de **lógica de negócio** (`AreaRiscoService`, `GeminiAIService`, `OpenWeatherService`), onde as regras de negócio são aplicadas e a interação com os repositórios e APIs externas ocorre.
* **`controller/`:** Define os **endpoints da API REST**, atuando como a interface de comunicação da aplicação.
* **`config/`:** Pacote dedicado a **classes de configuração** da aplicação, incluindo as para as APIs externas.

---

### Como Executar o Projeto

Para colocar o AquaSense em funcionamento, siga estas etapas:

1.  **Clone o Repositório:**
    ```bash
    git clone [URL_DO_SEU_REPOSITORIO]
    cd aquasense
    ```
2.  **Configurar o Banco de Dados MySQL:**
    * Certifique-se de ter uma instância do **MySQL Server** instalada e em execução.
    * Crie um banco de dados dedicado para o AquaSense (ex: `CREATE DATABASE aquasense_db;`).
    * Crie um usuário com permissões de acesso a este banco de dados.
3.  **Configurar Chaves de API e Propriedades:**
    * Obtenha sua chave de API do **Google Gemini**.
    * Obtenha sua chave de API do **OpenWeatherMap**.
    * Atualize o arquivo `src/main/resources/application.properties` (ou `application.yml`) com as configurações do MySQL e suas chaves de API:

    ```properties
    # application.properties

    # Configurações do MySQL
    spring.datasource.url=jdbc:mysql://localhost:3306/aquasense_db?useSSL=false&serverTimezone=UTC
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.username=seu_usuario_mysql
    spring.datasource.password=sua_senha_mysql

    # Configurações do JPA/Hibernate
    spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
    spring.jpa.hibernate.ddl-auto=update # Sugerido para desenvolvimento. Para produção, use 'validate' ou Flyway/Liquibase.
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true

    # Configurações do Google Gemini API
    gemini.api.key=SUA_CHAVE_GEMINI_AQUI

    # Configurações da OpenWeather API
    openweather.api.key=SUA_CHAVE_OPENWEATHER_AQUI
    openweather.api.base-url=https://api.openweathermap.org/data/2.5/weather
    openweather.api.units=metric # ou 'imperial'
    ```
4.  **Adicionar Dependências Necessárias:**
    * Certifique-se de que seu `pom.xml` inclui as dependências para o **MySQL Connector/J** e **Spring WebFlux** (para o `WebClient`):

    ```xml
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version> </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>

        </dependencies>
    ```

5.  **Compilar e Iniciar a Aplicação:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
    Alternativamente, você pode executar a classe principal `AquasenseApplication.java` diretamente do seu IDE.

A aplicação será iniciada, conectando-se ao MySQL e gerenciando o schema do banco de dados conforme suas entidades. A partir daí, você poderá interagir com a API REST pelos endpoints definidos.

---

### Membros da Equipe

* **Nicolas Dobbeck**
* **Jose Bezerra Bastos Neto**
* **Thiago Henry**

---
