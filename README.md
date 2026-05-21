# Library Loan Management System
## Apache Derby + JDBC with Transaction Management & Performance Evaluation

---

## Project Overview

The **Library Loan Management System** is a console-based database application developed using **Java JDBC** and **Apache Derby Embedded Database**. The project demonstrates practical implementation of database connectivity, transaction management, JDBC optimization techniques, and performance benchmarking.

The system is designed to simulate real-world library operations while emphasizing database consistency, modular architecture, and efficient JDBC execution strategies.

---

# Objectives

- Implement JDBC-based database connectivity using Apache Derby
- Demonstrate explicit transaction management using commit, rollback, and savepoints
- Perform CRUD operations on members, books, and loan records
- Benchmark various JDBC execution strategies
- Analyze transaction overhead and query performance
- Apply JDBC best practices and layered architecture principles

---

# Technologies Used

| Technology | Purpose |
|---|---|
| Java | Core application development |
| JDBC | Database connectivity |
| Apache Derby | Embedded relational database |
| Eclipse IDE | Development environment |

---

# Project Features

## Database Features

- Embedded Apache Derby database integration
- Normalized relational schema design
- Primary key and foreign key constraints
- Indexed query optimization
- Automatic database initialization

---

## Core Functionalities

- Register library members
- Add books to the catalog
- Process book loans
- Return borrowed books
- View members, books, and loan records
- Execute performance benchmark tests
- Demonstrate transaction management concepts

---

# Project Architecture

The project follows a layered architecture with proper separation of concerns.

## Package Structure

```text
Regd_2341001122
│
├── benchmark
│   └── PerformanceEvaluator.java
│
├── business
│   └── BusinessLogic.java
│
├── connection
│   ├── ConnectionManager.java
│   └── DatabaseInitializer.java
│
├── transaction
│   └── TransactionService.java
│
└── ui
    └── MainApp.java
