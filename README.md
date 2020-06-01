# API Lista Restrita
## Sobre

Este projeto tem como objetivo fornecer uma api de escrita e leitura de lista restrita de documentos.

## Arquitetura

- Java 11
- Spring-boot 2
- Spring-data-jpa
- Postgre
- Redis
- EhCache

## Pre-requisitos para exeutar local

- Java 11
- Docker e Docker-compose

## Startup local

1 - Subir os containers do postgre e redis
Ex.: docker-compose -f src/main/resources/docker/docker-compose.yml up

2 - Subir a aplicação
Ex.: mvn spring-boot:run

3 - Para testar basta acessar a url: http://localhost:8081/swagger-ui.html#/ no seu browser
