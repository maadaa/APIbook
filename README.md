[![Build Status](https://travis-ci.org/moreiraMD/APIbook.svg?branch=master)](https://travis-ci.org/moreiraMD/APIbook)
# APIbooks
Simples serviço para fornecer uma API para consulta de livros e cadastro.

# Rodando o projeto

Tenha na sua máquina as seguintes tecnologias:
- Docker
- Java 11
- Maven 3

Primeiro vamos subir nosso container para o banco de dados, um mysql:
```sh
$ docker run -d -p 3306:3306 --name=guiabolso-mysql --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=root" --env="MYSQL_DATABASE=guiabolso" mysql
```
O segundo passo é o build do projeto:
```sh
$ mvn clean install package
```

Nosso terceiro passo é criar a imagem para o Docker(Lembrando que todos os comandos são feitos dentro da pasta do projeto):

```
$ docker build -t apibooks:latest .
```

Já vamos ter os testes executados, build do projeto com todas suas dependências e nossa imagem para o quarto passo, vamos rodar nossa imagen e criar um link com o do mysql:
```sh
$ docker run -d \
    --name api-books \
    --link guiabolso-mysql:mysql \
    -p 8080:8080 \
    -e DATABASE_HOST=guiabolso-mysql \
    -e DATABASE_PORT=3306 \
    -e DATABASE_NAME=guiabolso \
    -e DATABASE_USER=root \
    -e DATABASE_PASSWORD=root \
    apibooks
```

Com o projeto rodando é só acessar seu [LocalHost:8080/books](http://localhost:8080/books) para inserir um livro ou listar.

# Arquitetura

Para realizar este teste foram escolhidas as seguintes tecnologias:

- Spring Framework
- Java 11
- MySql
- Docker