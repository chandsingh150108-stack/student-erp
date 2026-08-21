<<<<<<< HEAD
# Student ERP

A full-stack college management and student information system built with Java Spring Boot, MySQL, and React.

## Features

- **Dashboard** — Role-based dashboards for Admin, Faculty, and Students
- **Student & Faculty Management** — CRUD operations with search
- **Academic Management** — Departments, Programs, Academic Years, Semesters, Courses
- **Sections & Enrollment** — Student-section assignments, faculty-course assignments
- **Attendance** — Class sessions, attendance recording, percentage calculation
- **Timetable** — Room, time-slot, and schedule management
- **Examinations & Results** — Exam scheduling, marks entry, result viewing
- **Fees & Payments** — Fee structures, payment tracking, balance calculation
- **Library** — Book management, issuing, returns, overdue tracking
- **Announcements & Notifications** — System-wide and targeted notifications
- **Events & Scholarships** — Event registration, scholarship management
- **Backlogs & Complaints** — Backlog tracking, complaint submission and resolution

## Tech Stack

- **Backend:** Java 21, Spring Boot, Spring Security, Spring Data JPA, Hibernate, Maven
- **Frontend:** React, TypeScript, Vite
- **Database:** MySQL 8
- **Infrastructure:** Docker, Docker Compose, Nginx

## Prerequisites

- [Docker](https://docs.docker.com/get-docker/) (v20.10+)
- [Docker Compose](https://docs.docker.com/compose/install/) (v2.0+)

That's it. No Java, Maven, or Node.js installation required on your machine — everything runs inside Docker containers.

## Quick Start

### 1. Clone the repository

```bash
git clone https://github.com/
cd crm
```

### 2. Start all services

```bash
docker compose up --build
```

This will:
- Build the Spring Boot backend (compiles with Maven inside a container)
- Build the React frontend (compiles with Vite inside a container)
- Start MySQL 8 with health checks
- Wire everything together on a shared Docker network

First build takes a few minutes. Subsequent starts are faster.

### 3. Access the application

| Service    | URL                      |
|------------|--------------------------|
| Frontend   | http://localhost:80       |
| Backend API| http://localhost:8080     |
| MySQL      | localhost:3306            |

### 4. Log in

Use the seed credentials created on first startup:

| Role        | Username    | Password      |
|-------------|-------------|---------------|
| Admin       | admin       | admin123      |
| Faculty     | faculty1    | faculty123    |
| Student     | student1    | student123    |

## Stopping the application

```bash
docker compose down
```

To also remove the database volume (fresh start next time):

```bash
docker compose down -v
```

## Rebuilding after code changes

```bash
docker compose up --build
```

Only the services with changed code will rebuild.

## Project Structure

```
├── backend/              # Spring Boot application
│   ├── src/
│   │   ├── main/java/com/studenterp/
│   │   │   ├── config/       # Security, CORS, data init
│   │   │   ├── controller/   # REST controllers
│   │   │   ├── dto/          # Request/Response DTOs
│   │   │   ├── entity/       # JPA entities
│   │   │   ├── exception/    # Error handling
│   │   │   ├── repository/   # Spring Data repos
│   │   │   ├── security/     # JWT, auth filters
│   │   │   └── service/      # Business logic
│   │   └── main/resources/
│   │       └── application.properties
│   ├── pom.xml
│   └── Dockerfile
├── frontend/             # React + TypeScript + Vite
│   ├── src/
│   ├── nginx.conf        # Reverse proxy to backend API
│   └── Dockerfile
├── docker-compose.yml    # Orchestrates all services
└── README.md
```

## API Overview

All endpoints are prefixed with `/api/`. Protected endpoints require a Bearer token obtained from `/api/auth/login`.

```
POST /api/auth/login        # Authenticate and get JWT token
POST /api/auth/register     # Register a new user

GET    /api/departments     # List departments
POST   /api/departments     # Create department (Admin)
PUT    /api/departments/{id}# Update department (Admin)
DELETE /api/departments/{id}# Delete department (Admin)

GET    /api/students        # List students
POST   /api/students        # Create student (Admin)
GET    /api/students/{id}   # Get student details

GET    /api/faculty         # List faculty
POST   /api/faculty         # Create faculty (Admin)

GET    /api/courses         # List courses
POST   /api/courses         # Create course (Admin)

# Similar CRUD patterns for: programs, academic-years, semesters,
# sections, exams, attendance, fees, books, events, scholarships,
# complaints, announcements, notifications, and more.
```

## Environment Variables

Configured in `docker-compose.yml`:

| Variable                      | Default                  | Description           |
|-------------------------------|--------------------------|-----------------------|
| `MYSQL_ROOT_PASSWORD`        | `rootpassword`           | MySQL root password   |
| `MYSQL_DATABASE`             | `student_erp`            | Database name         |
| `MYSQL_USER`                 | `studenterp`             | Application DB user   |
| `MYSQL_PASSWORD`             | `StudentERP2026!`        | Application DB pass   |
| `SPRING_DATASOURCE_URL`      | (set in compose)         | JDBC connection URL   |
| `SPRING_DATASOURCE_USERNAME` | (set in compose)         | DB username           |
| `SPRING_DATASOURCE_PASSWORD` | (set in compose)         | DB password           |


=======
# student-erp
>>>>>>> aa3cd588c78785fa2d7a6bc151390042eef471da
