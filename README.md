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

```
---
Database Schema
Members Table
Column Name	Data Type
MemberID	INT
Name	VARCHAR
Email	VARCHAR
ActiveLoans	INT
Books Table
Column Name	Data Type
BookID	INT
Title	VARCHAR
Author	VARCHAR
ISBN	VARCHAR
Available	BOOLEAN
Loans Table
Column Name	Data Type
LoanID	INT
BookID	INT
MemberID	INT
LoanDate	DATE
ReturnDate	DATE
Transaction Management

The application demonstrates explicit transaction management using JDBC.

Transaction Features
Auto-commit disabled during transactional operations
Explicit commit and rollback handling
Savepoint implementation
Constraint violation handling
ACID property demonstration
Loan Processing Workflow

During loan processing, the following operations are executed within a single transaction:

Verify book availability
Update book availability status
Insert loan record
Update member active loan count

If any operation fails, rollback restores database consistency.

Performance Benchmarking

The system includes a benchmarking module to evaluate different JDBC execution strategies.

Benchmark Tests Performed
1. Batch Insert vs Individual Insert
Individual insertion of 1000 records completed in 269.3094 ms
Batch insertion of 1000 records completed in 283.3977 ms
Throughput comparison was measured for both strategies

2. Statement vs PreparedStatement
Statement execution time: 3.0601 ms
PreparedStatement execution time: 0.2257 ms
PreparedStatement demonstrated significantly better performance and security

3. Query Performance
Full-table scan execution time: 0.7681 ms
Indexed lookup execution time: 9.5048 ms
Query optimization and indexing behavior were analyzed

4. Transaction Granularity
Per-operation commit time: 22.8383 ms
Single batch commit time: 7.6381 ms
Batched commits significantly improved throughput

Overall Performance Observations

PreparedStatement improved both performance and security
Batch commit reduced transaction overhead
Indexing becomes beneficial as database size increases
JDBC optimization techniques improved scalability and efficiency
Apache Derby successfully demonstrated embedded database performance characteristics
JDBC Best Practices Implemented
Use of PreparedStatement for parameterized queries
Proper exception handling
Explicit transaction management
Layered architecture design
try-with-resources for automatic resource cleanup
Benchmark-based performance evaluation


How to Run the Project
Step 1: Install Requirements
Java
Eclipse IDE
Apache Derby Database
Step 2: Add Derby Libraries

Add the following JAR files to the project build path:

derby.jar
derbytools.jar
Step 3: Run the Application

Execute the following file:

ui/MainApp.java

The embedded Derby database and schema will initialize automatically during application startup.

Sample Console Output
====================================================================================================
LIBRARY LOAN MANAGEMENT SYSTEM
Apache Derby + JDBC with Transaction Management & Performance Evaluation
====================================================================================================

[1/3] Initializing database connection...
✓ Database connection established

[2/3] Creating schema...
✓ Executed: CREATE TABLE Members...
✓ Executed: CREATE TABLE Books...
✓ Executed: CREATE TABLE Loans...

[3/3] Seeding initial data...
✓ Seeded 4 members
✓ Seeded 5 books
Conclusion

The project successfully demonstrates the implementation of JDBC database connectivity, transaction management, performance benchmarking, and database optimization using Apache Derby Embedded Database. The system validates ACID transaction properties, modular software architecture, and practical JDBC performance analysis techniques in Java.


6th Semester Project

Developed By---
### **Ummatul Ayesha** 

B.Tech CSE (Cyber Security)
Regd No: 2341001122
---
