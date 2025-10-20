# 🚗 Back End Auto Control

API RESTful desenvolvida em **Java com Spring Boot** para gerenciar o controle de estoque e acesso veicular em ambientes *on-premise*, com integração a sistemas externos de vistoria e consulta de dados veiculares.

---

## 📦 Tecnologias Utilizadas

- **Linguagem**: Java 17  
- **Framework**: Spring Boot 3.2.5  
- **Banco de Dados**: MariaDB  
- **Persistência**: Spring Data JPA + Hibernate  
- **Outras Dependências**:
  - Lombok (redução de boilerplate)
  - Spring HATEOAS (links auto-relacionados em respostas)
  - Apache HttpClient 5 (comunicação HTTP avançada)
  - Jsoup (parse de HTML para integrações externas)
  - JavaMail (via `javax.mail`) – para futuras funcionalidades de notificação

---

## 🛠️ Configuração Local

### Pré-requisitos

- Java 17 (ou superior)
- Maven 3.6+
- MariaDB rodando localmente na porta **3310**
- Banco de dados `controleVeiculos` criado

### Passos para Execução

1. **Clone ou baixe o projeto**  
2. **Configure o banco de dados**  
   Certifique-se de que o MariaDB está rodando com as credenciais abaixo (ou ajuste conforme necessário):

   ```properties
   spring.datasource.url=jdbc:mariadb://localhost:3310/controleVeiculos
   spring.datasource.username=root
   spring.datasource.password=aspago@1910
