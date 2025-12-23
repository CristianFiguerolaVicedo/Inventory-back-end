# Inventory App - Back-end

## This is the back-end part of a whole inventory project. You can find the front-end repository in this link: <https://github.com/CristianFiguerolaVicedo/Inventory-front-end>

This app was made using `Java` for the backend with `Springboot` and `JavaScript` with `React` for the front-end

The intention of this project is to help small and medium e-commerces manage their inventory in an easy, clean and simple way and to be an alternative to the tipically used excel.

This inventory wants to be as much user-friendly as possible, making it perfect for everyone to use wether you have high technological knowledge or not.

### Features

1. Entity
2. DTO
3. Repository
4. Service
5. Controller
6. Config
7. Security
8. Util

## Entity

We have many entities which are the tables of the database:
- Category
- Event
- Expense
- Income
- Product
- Profile
- Sale
- SaleDetail (which is the intermediate table for the many to many relationship of sale and product)

## DTO

We have the DTO's which are the data we receive when we do a request:
- Auth Dto (which is the one in charge of the authorization of the user)
- Category Dto
- Event Dto
- Expense Dto
- Income Dto
- Product Dto
- Profile Dto
- Sale Dto
- Sale Detail Dto

## Repository

The repositories serve as an interface for the methods we will use on the service, they are auto-made using JPARepository which uses keywords on the methods name to implement the logic without the need to type it manually:
- Category Repository
- Event Repository
- Expense Repository
- Income Repository
- Product Repository
- Profile Repository
- Sale Repository

## Service

This is the part of the code which contains the main logic of the app having the methods and the logic to do all the operations:
- AppUserDetails Service (this is the logic to persist the user information between pages on the front-end)
- Category Service
- Dashboard Service
- Event Service
- Excel Service (the logic to download the product list in excel format)
- Expense Service
- Income Service
- Product Service
- Profile Service
- Sale Service

## Controller

This files contain the calls to the database and the methods to make the calls which use the service methods:
- Category Controller
- Dashboard Controller
- Event Controller
- Excel Controller (this controller is in charge for the calls to make the excel file)
- Expense Controller
- Filter Controller (in charge of applying the filters to the products)
- GlobalException Handler (in charge of showing the custom exceptions)
- Home Controller
- Income Controller
- Product Controller
- Profile Controller
- Sale Controller

## Config

This folder only contains the Security Config file, which is the one who limits the endpoints to make certain endpoints available without the need of the token. Also, in this file is the password encode method to store the password of each user in the database encrypted and not raw.

## Security

In this file we have the logic to filter and check if the requests have the token and the token is correct.

## Util

In this folder there are 2 files:
- JwtUtil: Here we have all the methods related to the token: extract the username from a token, extract the expiration time, extract one particular claim or all of them, check if the token is expired, validate the token and generate the token.

- Product Status: An enum for the status field of a product: IN_STOCK, SOLD_OUT or COMING_SOON

### You can see more details and screenshots in the portfolio of my web: <https://cristianfigvic.free.nf>

## The Postman collection is also uploaded to this repository in order to be able to test the endpoints.

## In order to run the project there are some variables that the user needs to fill:

- To run it locally: In the `application.properties` file, first you have to be sure that the `spring.profiles.active=prod` is commented. There are 2 variables:
    - MYSQL_PASSWORD: The password of your local mysql database.
    - FRONTEND_URL: The URL of the frontend part of the project.

- To run it in production: First, in the application.properties file, you have to make sure the `spring.profiles.active=prod` has to be active, then, in the `application-prod.properties`, there are 3 variables, which are the ones in charge of connecting to the production database, a PostgreSQL one:
    - `POSTGRESQL_PASSWORD`: The password of the production database.
    - `POSTGRESQL_USERNAME`: The username of the database.
    - `POSTGRESQL_URL`: The external host of the database.


