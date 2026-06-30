# Visa Tracking Dj (Agency & Hotel Service)

## Project Purpose
**Visa Tracking Dj** acts as the microservice responsible for managing external entities that interact with tourists, primarily **Travel Agencies** and **Hotels**. It tracks hotel check-ins, manages agency mappings to tourists, and handles complaints and ratings to ensure tourist safety and service quality.

## Core Components
### Entities (Data Models)
- **`AgencyEntity`**: Represents a travel agency operating in the system.
- **`HotelEntity`**: Represents registered hotels where tourists stay.
- **`HotelCheckInEntity`**: Logs the check-in and check-out records of tourists at hotels.
- **`ComplaintEntity`**: Stores complaints filed regarding agencies or services.
- **`AgencyRatingEntity`**: Stores feedback and ratings for travel agencies.
- **`AgencyTouristMappingEntity`**: Maps tourists to their responsible travel agency.

### Controllers (API Endpoints)
- **`AgencyController` & `AgencyRatingController`**: Endpoints for managing travel agencies and their ratings.
- **`HotelController` & `HotelCheckInController`**: Endpoints to manage hotels and track tourist check-ins.
- **`ComplaintController`**: Handles the submission and retrieval of complaints.

## Security Overview
The application is secured using **Spring Security** and **JWT**.
- The `SecurityConfig` ensures that sensitive operations (like submitting complaints or accessing check-in logs) require an authenticated token.
- Validates JWTs issued by the main Authentication Service.

## Technologies Used
- Java 21
- Spring Boot 3.1.5
- Spring Data JPA & JDBC
- Spring Security & JWT
- MySQL Connector
- SpringDoc OpenAPI (Swagger UI)

## Getting Started
1. Clone the repository.
2. Update `application.properties` with your MySQL database credentials.
3. Build the project using Maven: `./mvnw clean install`
4. Run the application: `./mvnw spring-boot:run`

## API Documentation
Once the application is running, access the Swagger UI at:
`http://localhost:<port>/swagger-ui.html`
