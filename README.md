# Know Your Neighborhood - API

This repository contains the RESTful API for the "Know Your Neighborhood" (KYN)
platform. It manages user authentication, store listings, and system
integrations.

## ✨ Features

- **Hybrid Authentication:** Supports both standard email/password login and
  Social Login (Google & Facebook) via OAuth2.
- **Security & Authorization:** Implements stateless session management using
  Spring Security and JSON Web Tokens (JWT).
- **Business Logic:** Full CRUD operations for local store management and
  neighborhood-based filtering.
- **Slack Integration:** Automatically forwards contact form inquiries to a
  designated Slack channel.
- **Global Exception Handling:** Robust error management for 404 (Resource Not
  Found) and 400 (Bad Request) scenarios.

## 🛠️ Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3
- **Security:** Spring Security, OAuth2 Client, JWT
- **Persistence:** MySQL, Spring Data JPA, Hibernate
- **Build Tool:** Maven

## ⚙️ Setup & Installation

1. **Clone the repository:** `git clone <repo-url>`
2. **Database Configuration:**
   - Create a MySQL database.
   - Update `src/main/resources/application.yml` with your database credentials
     and OAuth2 client IDs.
3. **Build the project:**
   - `./mvnw clean install -DskipTests`
4. **Run the application:**
   - `./mvnw spring-boot:run`

## 🔑 Core API Endpoints

| Method | Endpoint          | Access         |
| :----- | :---------------- | :------------- |
| POST   | `/auth/login`     | Public         |
| GET    | `/api/stores`     | Public         |
| POST   | `/api/stores`     | Private (User) |
| GET    | `/api/user/me`    | Private (User) |
| POST   | `/api/slack/send` | Public         |
