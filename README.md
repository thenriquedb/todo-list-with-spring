![img.png](header.png)

# Todo List REST API
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)![Render](https://img.shields.io/badge/Render-%46E3B7.svg?style=for-the-badge&logo=render&logoColor=white)

Este projeto foi desenvolvido como parte do curso gratuito de Java oferecido pela Rocketseat. Trata-se de uma API Rest para gerenciamento de lista de tarefas, construída em Java com o framework Spring Boot.


## Características do Projeto
- **Separação de Responsabilidades**: Implementamos uma estrutura de código que segue as boas práticas de separação de responsabilidades entre controllers e repositórios. Isso torna o código mais organizado e de fácil manutenção.
- **Filtro para Autenticação e Validação**: Criamos um filtro para a rota `/tasks` que garante a autenticação do usuário e valida as solicitações. Isso aumenta a segurança e a integridade dos dados.
- **Error Handler**: Desenvolvemos um mecanismo de tratamento de erros para fornecer respostas claras e adequadas em caso de problemas durante as solicitações.

### Requisitos
- **Java >= 17**;
- **Maven**;
- **Rest client**;

### Depedências
- **Spring JPA**;
- **Lombok**;
- **BCrypt**;
- **H2 Database**;

## API

### POST ``/users``

**Request body**
```json
  {
        "name": string,
        "username": string,
        "password": string
  }
```

**Response**
```json
    {
        "id": UUID,
        "username": string,
        "name": string,
        "createdAt": YYYY-MM-DDThh:mm:ss
    }
```

### POST ``/tasks``

**Header**
```
Authorization: Basic Base64 (username:password)
```

**Request**

```json
  {
      "title": string,
      "description": string,
      "priority": 0 | 1 | 2 | 3 | 4,
      "startAt": YYYY-MM-DDThh:mm:ss,
      "endAt": YYYY-MM-DDThh:mm:ss
  }

``` 
**Response**

```json
  {
        "id": UUID,
        "title": string,
        "description": string,
        "priority": 0 | 1 | 2 | 3 | 4,
        "startAt": YYYY-MM-DDThh:mm:ss,
        "endAt": YYYY-MM-DDThh:mm:ss,
        "createdAt": YYYY-MM-DDThh:mm:ss,
        "userId": UUID
  }
```

### POST ``/tasks``

**Header**
```
Authorization: Basic Base64 (username:password)
```
**Response**

```json
  {
        "id": UUID,
        "title": string,
        "description": string,
        "priority": 0 | 1 | 2 | 3 | 4,
        "startAt": YYYY-MM-DDThh:mm:ss,
        "endAt": YYYY-MM-DDThh:mm:ss,
        "createdAt": YYYY-MM-DDThh:mm:ss,
        "userId": UUID
  }[]
```

### PUT ``/tasks/{id}``

**Header**
```
Authorization: Basic Base64 (username:password)
```


**Response**

```json
  {
        "id": UUID,
        "title": string,
        "description": string,
        "priority": 0 | 1 | 2 | 3 | 4,
        "startAt": YYYY-MM-DDThh:mm:ss,
        "endAt": YYYY-MM-DDThh:mm:ss
  }
```
**Response**

```json
  {
        "id": UUID,
        "title": string,
        "description": string,
        "priority": 0 | 1 | 2 | 3 | 4,
        "startAt": YYYY-MM-DDThh:mm:ss,
        "endAt": YYYY-MM-DDThh:mm:ss,
        "createdAt": YYYY-MM-DDThh:mm:ss,
        "userId": UUID
  }
```

## Melhorias
- [ ] Adicionar endpoint para excluir tarefa;
- [ ] Documentar a API utilizando Swagger;
- [ ] Adicionar testes para cada endpoint;
- [ ] Substituir o H2 para o Postgress;
- [ ] Melhorar o processo de autenticação.