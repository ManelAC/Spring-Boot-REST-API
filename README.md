# Spring Boot REST API
A Spring Boot REST API is a Spring Boot based REST API that allows its users to create accounts, register movies and make recommendations.

---

## How to use
In order to run this API we need to:
- Compose up the `docker-composer-database.yml` file to create the container for the database.
- Go the the `/api` folder and run the command: `./mvnw clean package`
- Compose up the `docker-composer-api.yml` file to create the container for the API.

Once we have both containers running, we can interact with the API with any tool that allows us to do HTTP requests, like Postman.

We can also use the Swagger-based UI to interact with the API. To do that, we only need to have both containers running and then access the address `http://localhost:8080/swagger-ui/index.html` in any browser and follow the instructions.

![Swagger UI](https://cdn.discordapp.com/attachments/818865528734351370/1067609319819972628/Swagger-UI-1.png)

---

## Tests
In order to be able to run all tests without errors, the container with the database must be running.

---

## Technologies
This project has been created using:
- Spring Boot
- Java
- Docker
- Hibernate
- JPA
