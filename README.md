# Online Certificate Verification System (Java + Spring Boot)

A syllabus-aligned backend project using Java classes/objects, encapsulation, methods, exception handling, Spring Boot REST controllers, layered architecture, dependency injection, Spring Data JPA repositories and a persistent H2 database.

## Requirements
- JDK 17 or newer
- Maven 3.6+ (or open the `pom.xml` in IntelliJ IDEA)

## Run
From this folder:

```bash
mvn spring-boot:run
```

The server starts at `http://localhost:8080`. H2 console: `http://localhost:8080/h2-console` with JDBC URL `jdbc:h2:file:./data/certificatesdb`, username `sa`, and an empty password.

## API endpoints

### 1. Issue a certificate
`POST http://localhost:8080/api/certificates`

JSON body:
```json
{
  "recipientName": "Aarav Sharma",
  "courseName": "Java Programming Workshop",
  "organizationName": "PICT CSI Club"
}
```
The response contains a generated certificate ID such as `CERT-7A91BC02D4E8`. Save that ID for verification.

### 2. Verify a certificate
`GET http://localhost:8080/api/certificates/verify/CERT-7A91BC02D4E8`

An active certificate returns `valid: true`. A revoked certificate returns `valid: false`. An unknown ID returns HTTP 404.

### 3. View all certificates
`GET http://localhost:8080/api/certificates`

### 4. Revoke a certificate
`PATCH http://localhost:8080/api/certificates/CERT-7A91BC02D4E8/revoke`

### Duplicate prevention
- Each certificate ID is generated from a UUID and is the database primary key.
- The database enforces a unique certificate ID.
- The service rejects a second active certificate with the same recipient, course and organization (case-insensitive).
- Required fields are validated; blank values are rejected.

## Project structure
- `controller`: REST endpoints
- `service`: issue, verify and revoke business logic
- `repository`: Spring Data JPA database operations
- `entity`: certificate database model
- `dto`: request and message objects
- `exception`: central error handling and custom exceptions

## Important project limitation
This is a college-project prototype. It does not yet authenticate organizations, so do not expose the issue/revoke endpoints publicly. A production version should add organization login/roles and authorization before allowing certificates to be issued or revoked.
