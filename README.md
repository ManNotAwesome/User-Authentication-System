# User Authentication & Login System

A backend authentication and authorization system built using Java, Spring Boot, Spring Security, JWT, and MySQL.

## Features

* User Registration
* Login with JWT Token
* Password Encryption using BCrypt
* Protected Profile Endpoint
* Role-Based Authorization (USER / ADMIN)
* JWT Filter for Token Validation
* DTO-based Request and Response Handling

## Technologies Used

* Java
* Spring Boot
* Spring Security
* MySQL
* Hibernate / JPA
* JWT (JSON Web Token)
* Maven

## Project Structure

controller
service
repository
entity
dto
config
filter

## API Endpoints

POST /api/register
POST /api/login
GET /api/profile
GET /api/admin

## Roles

* USER → Access profile
* ADMIN → Access admin endpoint

## How to Run

1. Clone repository
2. Configure MySQL in application.properties
3. Run Spring Boot application
4. Test APIs using Postman

## Notes

Database password and local configuration should be updated before running locally.
