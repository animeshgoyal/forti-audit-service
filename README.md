# Forti Test Audit Service

### Technologies:

- Java
- Spring Boot 3.0
- MySQL
- H2 In-Memory DB
- Flyway
- Apache Tomcat
- JMS (with ActiveMQ)

### Run

##### Running on Local Dev:

```bash
java -jar target/forti-audit-service-1.0.jar --spring.profiles.active=dev
```

##### Running on Production (Like):

```bash
java -jar target/forti-audit-service-1.0.jar --spring.profiles.active=prod
```

### High Level Architecture

Application is built using an Kafka Event Driven Architecture (EDA) for the system which then uses Stream to save the events to DB


