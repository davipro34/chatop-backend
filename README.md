# ChaTop Backend API

## Description

The ChaTop API operates as the server for the ChaTop application.
Built with Spring Boot 3 and Java 17, it integrates Spring-doc OpenAPI and Swagger UI to provide thorough documentation.  

A Front-End application using this API is here :  
- https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring

## Features

The ChaTop API includes the following principal features:

- User Authentication with **JWT**
- User registration
- Rental creation with upload picture
- Rental display
- Rental update
- Sending Messages

## Installation

Prior to executing the ChaTop Backend API, adhere to these installation procedures.:

1. Clone this repository.
2. Install JDK 17 (Java Development Kit) on your local machine.
3. Install Maven locally.
4. Install MySQL (on your local machine or in a Docker container) and create a database for the application.
5. Create a free account on cloudinary.com for picture hosting. 
6. Configure the necessary environment variables in your system or within your IDE before running the application.

### Necessary application configuration
Copy src/main/resources/application.properties.sample and rename it at application.properties in the same directory.
Configure it with your information or add references to your environment variables.

### Possibly necessary environment variables
If you prefer to use environment variables
- `MYSQL_URL`: The url of your MySQL database. For example, `jdbc:mysql://localhost:3306/`.
- `MYSQL_DATABASE`: The name of your MySQL database. For example, `chatop_db`.
- `MYSQL_USER`: Your MySQL username.mysql_database
- `MYSQL_PASSWORD`: Your MySQL password.

- `COUDINARY_CLOUDNAME`: The name of your cloud.
- `CLOUDINARY_APIKEY`: Your Cloudinary API key
- `CLOUDINARY_APISECRET`: Your Cloudinary Secret key

### Necessary SQL database configuration

You can use this SQL script file for creating the tables in the database :

- [SQL Script](src/main/resources/scripts/sql/sql_minus_updated.sql)

And optionally you have the possibility to use a parameter in application.properties to (re-)initialize the database for you : 
- spring.sql.init.mode=

Consider configuring the `utf8mb4_unicode_ci` encoding for your database as well as the tables.

## Executing the application

### Launch the application by executing the JAR file

- Generate a JAR in the root folder of the project by executing:
  ```bash
  mvn clean install

- Launch the application by executing
  ```bash
  spring-boot:run

## Conducting tests using Postman

You can use [Postman](https://www.postman.com/) to test the ChaTop API.  
Download the Postman collection and environment script:

- [ChaTop API Postman Collection](src/main/resources/scripts/postnam/rental.postman_collection.json)  
- Note: The only two unauthenticated endpoints are `/api/auth/register` and `/api/auth/login`  
- It is recommended to create a first user by sending a POST request to the `/api/auth/register` endpoint and retrieve the returned `JWT` token to authenticate requests. 

## Conducting tests using Swagger UI
- Run the application ChaTop API
- Access the Swagger UI documentation at http://localhost:3001/api/swagger-ui/index.html
- Register a user by sending a POST request to the `/api/auth/register` endpoint
- Login by sending a POST request to the `/api/auth/login` endpoint
- Copy the JWT token from the response
- Click on the `Authorize` button on the top right corner of the Swagger UI page
- Paste the JWT token in the `Value` field and click on the `Authorize` button
- `You can now test the API endpoints`

## The following technologies are employed in the development of the ChaTop backend application:

- **Java:** Version 17
- **Spring Boot:** Version 3.2.3
- **Spring Security:** Starter for securing applications
- **Spring Web:** Starter for building web applications
- **MySQL Connector/J:** Runtime dependency for MySQL database connectivity
- **Project Lombok:** Used for reducing boilerplate code
- **JWT (JSON Web Token):** Java library for working with JSON Web Tokens
- **Spring Data JPA:** Starter for using Spring Data JPA with Hibernate
- **Springdoc OpenAPI:** Starter for OpenAPI and Swagger UI documentation
  - Version 2.3.0
- **Cloudinary-http45:** Cloudinary service (free account)  for uploading and hosting a picture
  - Version 1.38.0
