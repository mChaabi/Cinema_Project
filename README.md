# 🎬 CineManager — Backend API

RESTful backend for a cinema management and seat reservation platform, developed with **Java 21** and **Spring Boot**.

The API provides the core business logic for managing movies, genres, cinema rooms, screenings, users and reservations.

## 🛠️ Tech Stack

* **Java 21**
* **Spring Boot 3.x**
* **Spring Web**
* **Spring Data JPA / Hibernate**
* **Spring Security**
* **Spring Validation**
* **MySQL 8**
* **Lombok**
* **Maven**
* **JUnit 5**
* **Postman**

## ✨ Features

* 🎞️ Movie & genre management
* 🏢 Cinema room management
* 🕐 Screening management
* 💺 Seat availability management
* 🎟️ Reservation management
* 👥 User management
* 🔐 Authentication & authorization
* ✅ DTO validation
* 🌐 RESTful API

## ⚙️ Configuration

### Requirements

* JDK 21
* Maven 3.8+
* MySQL 8+

### Database

Create the database:

```sql
CREATE DATABASE aflami;
```

Configure `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/aflami?useSSL=false&serverTimezone=UTC
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

### Run the API

```bash
git clone https://github.com/mChaabi/Cinema_Project.git
cd Cinema_Project
mvn clean install
mvn spring-boot:run
```

API:

```text
http://localhost:8080
```

## 🔐 Default Admin Account

For the initial administration access:

```text
Username: admin
Password: admin
```

> Change the default credentials in a production environment.

## 🔗 Frontend

The Angular frontend is available here:

**Cinema FrontEnd:**
https://github.com/mChaabi/Cinema_FrontEnd

---

## 👥 Development Team

This project was developed collaboratively by:

* **Mohamed Chaabi**
* **Zaid Nouinou**
* **Hibatellah Jamyl**
* **Marwa El Khanchoufi**

> 🎬 CineManager is a collaborative Full-Stack project developed for educational and portfolio purposes.


⭐ Developed for educational and portfolio purposes.
