# SecurePay REST API

SecurePay is a Spring Boot RESTful API for managing payment transactions securely.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Authentication](#authentication)
- [Deployment](#deployment)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Introduction

SecurePay is designed to facilitate secure payment transactions. It provides endpoints for creating, retrieving payment transactions.

## Features

- Create new customer
- Save customers credit card info using encryption
- Update customers credit card info
- Create new payment
- Retrieve all payments
- Retrieve all payments by date interval
- Retrieve all payments by customer information
- Retrieve all payments by credit card information
- Secure authentication mechanisms
- Error Handling
- Data Validations

## Technologies Used

- Java(JDK17)
- Spring Boot(3.*)
- Spring Data JPA
- Spring Security
- H2 Database
- Maven 3.*
- Docker
- Swagger 3.*
- AES Encryption
- Unit Test(Junit5+ and Mockito)

## Getting Started

To get started with SecurePay, follow these steps:

1. Clone this repository.
2. Set up a H2 database and configure the database connection in `application.properties`.
3. Build the docker image.
   - ```docker build -t securepay . ```
4. Run the docker container.
   - ```docker run -p 8070:8070 securepay```
5. Test the API endpoints.
- Once the application is running, you can access the API endpoints using tools like Postman or curl.
- Use the provided API documentation or explore the swagger ui and use swagger functionalities.
6. Access the Swagger UI.
- Once the application is running, you can access the Swagger UI by navigating to `http://localhost:8070/swagger-ui.html` in your web browser.
- Swagger UI provides interactive documentation for exploring and testing the API endpoints.

## API Endpoints

The following endpoints are available:

- `GET /api/v1/customers`: Retrieve all customers.
- `POST /api/v1/customers`: Create new customer.
- `PUT /api/v1/customers/updateCreditCard`: Save or update customers credit card info.
- `POST /api/v1/payments`: Create new payment.
- `GET /api/v1/payments/search`: Search payments with query params(Params -> customerNumber, customerName or carNumber). At least one is mandatory.
- `GET /api/v1/payments/list`: List payments by date interval and pagination.(Params -> startDate an endDate). Date format should be like this `2024-06-01` and mandatory. Pagination is not mandatory but if needed add query params `page` and `size` param with integer value.

## Authentication

SecurePay uses Basic Auth for authentication. username: `root` and password: `password`.

## Swagger

SecurePay uses swagger ui for monitoring.

## Deployment

You can deploy SecurePay to any server or cloud platform that supports Java applications. Make sure to configure the database connection and environment variables accordingly.

## Testing

Unit tests are implemented using JUnit and Spring Boot Test framework. You can run the tests using Maven: `mvn test`.

## Contributing

Contributions to SecurePay are welcome! To contribute, fork this repository, make your changes, and submit a pull request. Please make sure to follow the existing code style and write unit tests for any new functionality.
- [Arda Altınay](https://github.com/ardaltinay) - Java Web Developer

