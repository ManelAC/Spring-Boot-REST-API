# Spring Boot REST API
 A Spring Boot REST API

---

## How to use

In order to run this API we need to:
- Compose up the `docker-composer-database.yml` file to create the container for the database.
- Go the the `/api` folder and run the command: `./mvnw package`
- Compose up the `docker-composer-api.yml` file to create the container for the API.

---

## Tests

In order to be able to run all tests without errors, the container with the database must be running.
