# GlobalLogic User Microservice

This project is a microservice built using **Spring Boot 2.5.14** and **Gradle**, which provides user creation and query functionality. The microservice includes data validation, error handling, and authentication using **JWT**. Data persistence is managed through an in-memory **H2** database.

## Table of Contents

- [Requirements](#requirements)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Setup and Run](#setup-and-run)
- [Usage](#usage)

## Requirements
1. All endpoints must accept and return only JSON, including error messages, and must return the appropriate HTTP status code. Please consider that exceptions must be handled.
2. Creates an endpoint `/signup` to creates an user in the database.
   - Email addresses must adhere to a regular expression to ensure they are in the correct format (e.g., aaaaaaa@undominio.algo). An error message should be returned if the email does not match the pattern.
   - Passwords must comply with a regular expression to validate their format. They must contain exactly one uppercase letter, two numeric digits (not necessarily consecutive), and any number of lowercase letters. The minimum and maximum lengths are 8 and 12 characters, respectively. For example, "a2asfGfdfdf4". If the password does not meet these criteria, an error message should be returned.
   - The name and phone number fields are optional.
   - Upon successful user creation, return the following user details:
     1. id: A unique identifier for the user (ideally a UUID generated by the database)
     2. created: The timestamp of when the user was created
     3. lastLogin: The timestamp of the user's most recent login
     4. token: A JSON Web Token (JWT) for authenticating API requests
     5. isActive: A boolean flag indicating whether the user's account is active Utilize Spring Data and an H2 in-memory database to persist user data. Implement password encryption to enhance security. If a user with the same email address already exists in the database, return an appropriate error message.
3. Creates an endpoint `/login` to query a registered user. It must use the token generated in the `/signup` endpoint to perform the query and return all the information of the persisted user. The token will be updated.

## Technology Stack
- **Java 11**.
- **Gradle 7.4**.
- **Spring Boot 2.5.14**.
- **H2 Database**.
- **JWT** for user authentication.

## Setup and Run

### Clone the Repository

```bash
git clone https://github.com/alearmas/globallogic-challenge.git
cd tienda-globallogic
```

### Build project
```bash
./gradlew build
./gradlew bootRun
```

The application will start on http://localhost:8080.

## Usage

### API endpoints
The service provides two main endpoints:

### 1. `api/v1/sign-up` - Create a new user
Allows user creation through a `POST` request. The input JSON must follow the format below:

```json
{
  "name": "String",
  "email": "String",
  "password": "String",
  "phones": [
    {
      "number": "long",
      "citycode": "int",
      "contrycode": "String"
    }
  ]
}
```

### 1. `api/v1/login` - User Login

Allows users to log in through a `POST` request. The input JSON must follow the format below:

#### Request
```json
{
  "email": "johndoe@example.com",
  "password": "Abcdef12"
}
```

#### Response (Success)
```json
{
  "id": "e5c6cf84-8860-4c00-91cd-22d3be28904e",
  "created": "2024-09-25T14:30:45Z",
  "lastLogin": "2024-09-25T14:30:45Z",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "isActive": true,
  "name": "John Doe",
  "email": "johndoe@example.com",
  "phones": [
    {
      "number": 1234567890,
      "citycode": 1,
      "contrycode": "55"
    }
  ]
}
```

#### Response (Error)
```json
{
  "error": [{
    "timestamp": "2024-09-25T14:32:10Z",
    "codigo": 400,
    "detail": "User already exists"
  }]
}
```