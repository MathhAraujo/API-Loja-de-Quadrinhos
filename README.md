# Desafio Técnico Back-end

Criação de uma API Rest de uma loja de quadrinhos semelhante a API da Marvel
API: https://developer.marvel.com

## Tecnologias utilizadas

Java 17
Spring Boot
Maven
MySQL
Swagger
Spring Security
Jwt Token
H2Database

## Decisões Arquiteturais

Java: Familiaridade técnica

Spring Boot: Serviço Rest e de Injeção de dependência.

MySQL: Familiaridade técnica

Swagger: Facilidade de documentação e testes

Testes unitários: Testes focados no funcionamento do código, com grande cobertura

Spring Security: Autenticação de usuário

H2Database: Utilizado para testes como banco de dados local

## Estrutura do projeto

Foi utilizada uma estrutura baseada na responsabilidade de níveis, tendo os service no package service, repository no package repository, etc.
O padrão DTO foi utilizado para transporte e o tratamento de exceptions se da pela classe RestExceptionHandler, não sendo tratados nos controllers.
Seguido também o padrão Service-Repository, deixando toda a business logic na camada de serviço e não dando acesso direto dos controlles aos repositórios.

```
📦 
├─ .idea
│  ├─ .gitignore
│  ├─ compiler.xml
│  ├─ dataSources.xml
│  ├─ encodings.xml
│  ├─ jarRepositories.xml
│  ├─ misc.xml
│  ├─ modules.xml
│  ├─ uiDesigner.xml
│  └─ vcs.xml
├─ .mvn
│  └─ wrapper
│     └─ maven-wrapper.properties
├─ HELP.md
├─ README.md
├─ demo.iml
├─ mvnw
├─ mvnw.cmd
├─ pom.xml
├─ src
│  ├─ main
│  │  ├─ java
│  │  │  └─ com
│  │  │     └─ example
│  │  │        └─ demo
│  │  │           ├─ DemoApplication.java
│  │  │           ├─ Exceptions
│  │  │           │  ├─ InvalidArgumentException.java
│  │  │           │  └─ ResourceNotFoundException.ja
│  │  │           ├─ Infra
│  │  │           │  └─ RestExceptionHandler.java
│  │  │           ├─ configs
│  │  │           │  ├─ CupomConfig.java
│  │  │           │  ├─ EncomendaConfig.java
│  │  │           │  ├─ QuadrinhoConfig.java
│  │  │           │  ├─ SecurityConfig.java
│  │  │           │  └─ SpringDocConfig.java
│  │  │           ├─ controllers
│  │  │           │  ├─ AuthenticationController.java
│  │  │           │  ├─ CupomController.java
│  │  │           │  ├─ EncomendaController.java
│  │  │           │  └─ QuadrinhoController.java
│  │  │           ├─ dto
│  │  │           │  ├─ AuthenticationDTO.java
│  │  │           │  ├─ CupomDTO.java
│  │  │           │  ├─ EncomendaDTO.java
│  │  │           │  ├─ LoginResponseDTO.java
│  │  │           │  ├─ QuadrinhoDTO.java
│  │  │           │  └─ RegisterDTO.java
│  │  │           ├─ filter
│  │  │           │  └─ SecurityFilter.java
│  │  │           ├─ models
│  │  │           │  ├─ Cupom.java
│  │  │           │  ├─ Encomenda.java
│  │  │           │  ├─ Quadrinho.java
│  │  │           │  ├─ Raridades.java
│  │  │           │  ├─ User.java
│  │  │           │  └─ UserRole.java
│  │  │           ├─ repository
│  │  │           │  ├─ CupomRepository.java
│  │  │           │  ├─ EncomendaRepository.java
│  │  │           │  ├─ QuadrinhoRepository.java
│  │  │           │  └─ UserRepository.java
│  │  │           └─ service
│  │  │              ├─ AuthorizationService.java
│  │  │              ├─ CupomService.java
│  │  │              ├─ EncomendaService.java
│  │  │              ├─ QuadrinhoService.java
│  │  │              ├─ TokenService.java
│  │  │              └─ UserService.java
│  │  └─ resources
│  │     └─ application.properties
│  └─ test
│     ├─ java
│     │  └─ com
│     │     └─ example
│     │        └─ demo
│     │           ├─ DemoApplicationTests.java
│     │           ├─ controllers
│     │           │  ├─ CupomControllerTest.java
│     │           │  ├─ EncomendaControllerTest.java
│     │           │  └─ QuadrinhoControllerTest.java
│     │           ├─ repository
│     │           │  ├─ CupomRepositoryTest.java
│     │           │  ├─ EncomendaRepositoryTest.java
│     │           │  ├─ QuadrinhoRepositoryTest.java
│     │           │  └─ UserRepositoryTest.java
│     │           └─ service
│     │              ├─ CupomServiceTest.java
│     │              ├─ EncomendaServiceTest.java
│     │              └─ QuadrinhoServiceTest.java
│     └─ resources
│        └─ application-test.properties
└─ target
   ├─ classes
   │  ├─ application.properties
   │  └─ com
   │     └─ example
   │        └─ demo
   │           ├─ DemoApplication.class
   │           ├─ Exceptions
   │           │  ├─ InvalidArgumentException.class
   │           │  ├─ MaxValueException.class
   │           │  ├─ MismatchedValuesException.class
   │           │  └─ ResourceNotFoundException.class
   │           ├─ Infra
   │           │  └─ RestExceptionHandler.class
   │           ├─ configs
   │           │  ├─ CupomConfig.class
   │           │  ├─ EncomendaConfig.class
   │           │  ├─ QuadrinhoConfig.class
   │           │  ├─ SecurityConfig.class
   │           │  └─ SpringDocConfig.class
   │           ├─ controllers
   │           │  ├─ AuthenticationController.class
   │           │  ├─ CupomController.class
   │           │  ├─ EncomendaController.class
   │           │  └─ QuadrinhoController.class
   │           ├─ dto
   │           │  ├─ AuthenticationDTO.class
   │           │  ├─ CupomDTO.class
   │           │  ├─ EncomendaDTO.class
   │           │  ├─ LoginResponseDTO.class
   │           │  ├─ QuadrinhoDTO.class
   │           │  └─ RegisterDTO.class
   │           ├─ filter
   │           │  └─ SecurityFilter.class
   │           ├─ models
   │           │  ├─ Cupom.class
   │           │  ├─ Encomenda.class
   │           │  ├─ Quadrinho.class
   │           │  ├─ Raridades.class
   │           │  ├─ User.class
   │           │  └─ UserRole.class
   │           ├─ repository
   │           │  ├─ CupomRepository.class
   │           │  ├─ EncomendaRepository.class
   │           │  ├─ QuadrinhoRepository.class
   │           │  └─ UserRepository.class
   │           └─ service
   │              ├─ AuthorizationService.class
   │              ├─ CupomService.class
   │              ├─ EncomendaService.class
   │              ├─ QuadrinhoService.class
   │              ├─ TokenService.class
   │              └─ UserService.class
   └─ test-classes
      ├─ application-test.properties
      └─ com
         └─ example
            └─ demo
               ├─ DemoApplicationTests.class
               ├─ controllers
               │  ├─ CupomControllerTest.class
               │  ├─ EncomendaControllerTest.class
               │  └─ QuadrinhoControllerTest.class
               ├─ repository
               │  ├─ CupomRepositoryTest.class
               │  ├─ EncomendaRepositoryTest.class
               │  ├─ QuadrinhoRepositoryTest.class
               │  └─ UserRepositoryTest.class
               └─ service
                  ├─ CupomServiceTest.class
                  ├─ EncomendaServiceTest.class
                  └─ QuadrinhoServiceTest.class
```

## Deploy

Altere em aplications.properties:

```
spring.datasource.url=jdbc:mysql://localhost:8080/{MySQLDatabase}
spring.datasource.username= {MySQLUsername}
spring.datasource.password= {MySQLPassword}
```

## Utilização da API

Para a documentação do Swagger: http://localhost/swagger-ui.html

### Autenticação

Para a realização de qualquer requisição para a API é necessário um Token.
Primeiro realize o cadastro com um login e senha.
Após o cadastro realize o login com o mesmo login e senha
Copie o token retornado e utilize-o no botão Authorize proximo a opção servers no swagger-ui
Ou utilize-o como header com o Postman ou programas semelhantes.

### Cadastro de Quadrinhos e Encomendas

Quadrinhos são cadastrados com o JSON:

``` 
{
  "raridade": "[COMUM, RARO]",
  "volume": 0,
  "titulo": "string",
  "editora": "string"
}
```

Encomendas são cadastados com o JSON:

```
{
  "quantidade": 0,
  "quadrinhoId": 0
}
```

Caso o usuário queira utilizar um cupom com a encomenda, será necessário passar seu id como parâmetro.

Cupons são cadastrados por meio da requisição /api/cupons/gerarCupom, retornando o cupom gerado automaticamente,
tendo 10% de chance de ser um cupom raro e com uma validade entre 2 e 12 meses.

Caso seja necessário o cadastro de um cupom personalizado, pode-se utilizar /api/cupons/gerarCupomCustomizado, que aceita o JSON:

```
{
  "raridade": "[COMUM, RARO]",
  "validade": "year-month-day"
}
```

Com validade não podendo ser anterior ao dia atual.

## Formatação de Exceptions

Caso alguma requisição seja feita para a API com um parâmetro inválido, será retornado
na resposta:

```
InvalidArgumentException{
 errorTime={dd-MM-yyyy HH:mm:ss},
 message= 'Estoque não pode ser menor que 0',
 httpCode= 400 BAD_REQUEST,
 rejectedValue= {value}
}
```

Caso alguma requisição de procura por Id = x for feita para a API, e o objeto com id = x
não existir, será retornado na resposta:

```
ResourceNotFoundException{
 errorTime= {dd-MM-yyyy HH:mm:ss},
 message= 'Quadrinho não encontrado',
 httpCode= 404 NOT_FOUND,
 id= {id}
}
```
