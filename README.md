## Descrição

Cada portador de cartão (cliente) possui uma conta com seus dados.
A cada operação realizada pelo cliente uma transação é criada e associada à sua  respectiva conta.
Cada transação possui um tipo (compra a vista, compra parcelada, saque ou pagamento), um valor e uma data de criação.
Transações de tipo **COMPRA** e **SAQUE** são registradas com **valor negativo**, enquanto transações de **PAGAMENTO** são registradas com **valor positivo**.

## Pré-requisitos

- Java 11
- Maven 3.8.0+ 
- SpringBoot 2.4.5
- H2 Database (Banco de dados em memória para testes e demonstrações)
- JUnit 5

## Como executar com Docker

- Acessar a pasta do projeto

```shell
cd case-pismo
```

- Executar o comando para fazer o build e gerar a imagem

```shell
mvn spring-boot:build-image
```

- Execute o programa através do comando abaixo

```shell
docker run -p 8080:8080 case-pismo
```

## Como executar manualmente

- Acessar a pasta do projeto

```shell
cd case-pismo
```

- Executar o comando para compilar o projeto

```shell
mvn clean install
```

- Execute o programa através do comando abaixo

```shell
mvn spring-boot:run
```

- Execute o programa em modo debug usando os parametros abaixo

```shell
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8787"
```

## Banco de dados

Para fins de desenvolvimento este projeto foi configurado para funcionar com JPA e H2 (banco de dados de memória).

Para acessar o console H2:

```shell
http://localhost:8080/h2-console
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:case-pismo
User Name: sa
Password: 
```

# Documentação da API com Swagger

Após o deploy é possível conferir a documentação da API em:

```shell
http://localhost:8080/swagger-ui/ 
```

# Documentação da API

**POST** - Criar conta
```shell
curl --location --request POST 'http://localhost:8080/accounts' \
--header 'Content-Type: application/json' \
--data-raw '{
    "document_number": "12345678900"
}'
```

**GET** - Listar todas as contas
```shell
curl --location --request GET 'http://localhost:8080/accounts'
```

**GET** - Listar conta pelo id
```shell
curl --location --request GET 'http://localhost:8080/accounts/{accountId}'
```

**POST** - Criar transação
```shell
curl --location --request POST 'http://localhost:8080/transactions' \
--header 'Content-Type: application/json' \
--data-raw '{
"account_id": 1,
"operation_type_id": 3,
"amount": 123.45
}'
```

**GET** - Listar conta e transações pelo id
```shell
curl --location --request GET 'http://localhost:8080/accounts/{accountId}/transactions'
```

# Testes

Os testes foram elaborados utilizando JUnit e Mockito.

**Executando os testes**

```shell
mvn test
```