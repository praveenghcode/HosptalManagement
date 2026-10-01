# Hospital Management System - Implementation Plan

## Overview
A Multi-Tenant Hospital Management System designed to manage multiple hospitals (Super Admin level) and individual hospital operations.
**Mobile Support**: The application will be designed as a Responsive Web Application (PWA) using Angular, ensuring it works seamlessly on mobile devices for patients and doctors.

## Technology Stack
-   **Frontend**: Angular (Web & Mobile via Responsive/PWA)
-   **Backend**: Java Spring Boot
-   **Database**: MySQL
-   **Version Control**: Git (GitHub)

## User Roles & Key Features
1.  **Super Admin**:
    -   Manage Hospitals (Onboard new hospitals).
    -   Manage Hospital Admins.
    -   System-wide settings and subscriptions.
2.  **Hospital Admin**:
    -   Manage Doctors, Staff, and Resources within their hospital.
    -   View Hospital Reports (Financial, Operational).
    -   Manage Departments/Specializations.
3.  **Doctor**:
    -   View Appointments.
    -   Manage Patient Medical Records.
    -   Prescriptions.connt
    -   Schedule/Availability management.
4.  **Patient**:
    -   **Mobile App Focus**: Easy appointment booking.
    -   View Medical History & Prescriptions.
    -   Profile management.

## Architecture
-   `/frontend`: Angular Application (Shared codebase for Admin/Web and Mobile views).
-   `/backend`: Spring Boot Application (REST API).

## Next Steps
1.  **Environment Check**: Java/Maven checked and verified in PATH. [COMPLETED]
2.  **Backend Setup**: Services & Controllers created for Hospital/Doctor. Code ready. [COMPLETED - PENDING RUN]
3.  **Frontend Setup**: Angular App, Login/Register UI, Dashboard UI. [COMPLETED]
4.  **Database**: Setup MySQL schema. [PENDING]
