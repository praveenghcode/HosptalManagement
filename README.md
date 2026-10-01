# Hospital Management System

This project consists of an Angular Frontend and a Spring Boot Backend.

## Prerequisites

To run this application, you must install the following software on your computer:

1.  **Node.js** (Already installed: v22.20.0)
2.  **Java JDK 17+**: [Download Here](https://www.oracle.com/java/technologies/downloads/)
3.  **Maven**: [Download Here](https://maven.apache.org/download.cgi)
4.  **MySQL Server**: [Download Here](https://dev.mysql.com/downloads/installer/)

## Setup & Run

### Database
1.  Install and start MySQL Server.
2.  Create a database named `hospital_db`.
    ```sql
    CREATE DATABASE hospital_db;
    ```
3.  Update the username and password in `backend/src/main/resources/application.properties` if they differ from `root`/`root`.

### Backend (Spring Boot)
1.  Open a terminal in the `backend` folder.
2.  Run the application:
    ```bash
    mvn spring-boot:run
    ```

### Frontend (Angular)
1.  Open a terminal in the `frontend` folder.
2.  Install dependencies (if not done):
    ```bash
    npm install
    ```
3.  Start the server:
    ```bash
    ng serve
    ```
4.  Open `http://localhost:4200`.
