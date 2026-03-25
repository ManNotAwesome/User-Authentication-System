# User Authentication Login System

A secure backend authentication system built using Spring Boot, Spring Security, JWT, MySQL, and Docker.
This project demonstrates user registration, login, JWT-based authorization, role-based access control, API documentation, unit testing, and containerized deployment.

---

## Features

* User Registration with encrypted password storage using BCrypt
* User Login with JWT token generation
* Protected Profile API using JWT authentication
* Role-based Admin endpoint access
* Global Exception Handling for cleaner API responses
* Swagger/OpenAPI documentation with Bearer token authorization
* Unit Testing using JUnit and Mockito
* Dockerized application for portable deployment
* MySQL database integration

---

## Tech Stack

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* MySQL
* JWT (JSON Web Token)
* Swagger / OpenAPI
* JUnit + Mockito
* Docker

---

## Project Structure

src/main/java/com/auth/userauth

├── config
├── controller
├── dto
├── entity
├── exception
├── filter
├── repository
├── service

---

## API Endpoints

| Method | Endpoint      | Description                   |
| ------ | ------------- | ----------------------------- |
| POST   | /api/register | Register new user             |
| POST   | /api/login    | Login and receive JWT token   |
| GET    | /api/profile  | Access profile using JWT      |
| GET    | /api/admin    | Admin-only protected endpoint |

---

## Swagger API Documentation

After running the application:

http://localhost:8080/swagger-ui/index.html

Use Swagger **Authorize** button and enter:

Bearer your_token_here

---

## Sample Register Request

```json
{
  "name": "test",
  "email": "test@test.com",
  "password": "1234"
}
```

## Sample Login Request

```json
{
  "email": "test@test.com",
  "password": "1234"
}
```

---

## Docker Run

Build image:

```bash
docker build -t user-auth-system .
```

Run container:

```bash
docker run -p 8080:8080 user-auth-system
```

---

## Unit Tests Added

* Login Success Test
* Duplicate Email Validation Test
* Invalid Password Test

---

## Security Features

* BCrypt password hashing
* JWT token expiration
* Bearer token authentication filter
* Role-based endpoint protection

---

## Future Improvements

* Refresh Token support
* Email verification
* Password reset flow
* Cloud deployment

---
