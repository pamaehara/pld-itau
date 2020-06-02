# API Lista Restrita
![Alt Text](https://github.com/pamaehara/pld-itau/blob/master/pld-itau.png)

## Sobre
Este projeto tem como objetivo fornecer uma api de escrita e leitura de documentos restritos, podendo se estender para uma API inteligente com regras de negócio.
Na proposta da imagem a ideia da API de leitura é de uma estrutura composta por grupos auto escaláveis de no minimo 3 spotIntances M5 na AWS. O serviço de leitura consulta 2 níveis de cache antes de chegar ao DB ReadOnly para isolar o DB e responder mais rápido as requisições. Já pelo lado dos serviços de escrita a ideia é isolar qualquer dependência dos sistemas legados e não bloquear nenhuma transação, pensando nisso pensei em criar uma fila SQS na AWS onde os sistemas legados enviariam os documentos que devem compor a lista restrita, mas também podemos pensar em outra solução com os sistemas legados utilizando atores para enviar as requisições direto ao serviço de escrita. 

## Arquitetura

- Java 11
- Spring-boot 2
- Spring-data-jpa
- Postgre
- Redis
- EhCache
- ArchUnit

## Pre-requisitos para exeutar local

- Java 11
- Docker e Docker-compose

## Startup local

1 - Subir os containers do postgre e redis
Ex.: docker-compose -f src/main/resources/docker/docker-compose.yml up

2 - Subir a aplicação
Ex.: mvn spring-boot:run

3 - Para testar basta acessar a url: http://localhost:8081/swagger-ui.html#/ no seu browser
