# User Authentication Login System

A production-style authentication backend built using Spring Boot, Spring Security, JWT, MySQL, Swagger, JUnit, and Docker.

This project demonstrates a complete authentication lifecycle including account registration, email OTP verification, JWT-based authorization, password recovery, resend OTP, role-based access control, testing, and containerized deployment.

---

## Features

### Authentication

* User Registration
* JWT-based Login Authentication
* Protected Profile Endpoint
* Admin Role Endpoint

### Email OTP Verification

* OTP sent during registration
* Account verification required before login
* OTP expires after 5 minutes
* Expired unverified users automatically removed
* Resend OTP support

### Password Recovery

* Forgot Password OTP via email
* Reset Password using OTP
* Invalid OTP handling
* Expired OTP handling

### Security

* BCrypt password hashing
* JWT token generation
* Bearer token authorization
* Role-based endpoint protection

### Engineering Enhancements

* Swagger/OpenAPI API documentation
* Global Exception Handling
* JUnit + Mockito Unit Testing
* Docker containerization
* MySQL integration

---

## Tech Stack

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* MySQL
* JWT
* Swagger / OpenAPI
* JUnit + Mockito
* Docker
* Java Mail Sender

---

## API Endpoints

| Method | Endpoint             | Description              |
| ------ | -------------------- | ------------------------ |
| POST   | /api/register        | Register new user        |
| POST   | /api/verify-otp      | Verify account using OTP |
| POST   | /api/resend-otp      | Resend OTP               |
| POST   | /api/login           | Login and receive JWT    |
| GET    | /api/profile         | Access profile using JWT |
| GET    | /api/admin           | Admin-only endpoint      |
| POST   | /api/forgot-password | Send password reset OTP  |
| POST   | /api/reset-password  | Reset password using OTP |

---

## Swagger Documentation

Run project and open:

http://localhost:8080/swagger-ui/index.html

For protected APIs:

Use Swagger **Authorize** button and enter:

Bearer your_token_here

---

## Sample Register Request

```json id="j6f1lz"
{
  "name": "test",
  "email": "test@test.com",
  "password": "1234"
}
```

---

## Sample Verify OTP Request

```json id="a2w8pd"
{
  "email": "test@test.com",
  "otp": "123456"
}
```

---

## Sample Forgot Password Request

```json id="n4m3kr"
{
  "email": "test@test.com"
}
```

---

## Sample Reset Password Request

```json id="s9u7yc"
{
  "email": "test@test.com",
  "otp": "123456",
  "newPassword": "new1234"
}
```

---

## Docker Run

Build image:

```bash id="r5v2xe"
docker build -t user-auth-system .
```

Run container:

```bash id="q8n6tm"
docker run -p 8080:8080 user-auth-system
```

---

## Unit Tests Added

* Login Success Test
* Duplicate Email Validation Test
* Invalid Password Test

---

## Future Improvements

* Refresh Token Support
* Cloud Deployment
* CI/CD Integration
