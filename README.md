[![Build Status](https://semaphoreci.com/api/v1/shourien/springrest/branches/master/shields_badge.svg)](https://semaphoreci.com/shourien/springrest)

## Customer Profile management REST Application
 
 This is a Spring Boot Application with a full-fledged REST API service exposed with Swagger 2.0 UI. 
 It allows for CRUD operations on the customer profile information.
 
 __*This application is hosted on Microsoft Azure Cloud.**__
 
 Click the following link to access the  __[DEMO](http://appdemos.org)__ 
 
Default 2 users in the system and their corresponding details for Basic in-memory login

| User          | Password      |
| ------------- |:-------------:|
| admin         | *password*    |
| user          | *password*    |

### Architecture Diagram
![Architecture Page](https://i.imgur.com/AMLcQCp.png)

### Technology Stack 
 * Spring Boot
 * Spring Rest
 * Spring JPA
 * Swagger 2.0
 * H2 in-memory DB
 * Docker Container
 * Open JDK 8
 * Maven 3
 
### Build Instructions
 If you need to run the project on Eclipse or IntelliJ following steps would help for a quick setup
 
 1. Open pom.xml with Intelij or Eclipse
 2. Run Main.java
 3. Open browser on http://localhost:8080/swagger-ui.html
 
### Docker Run Command
 If you require to directly run the service in docker environment then execute the following command to automatically pull and deploy locally from public docker cloud repository.
 
 `docker run -d -p 8080:8080 shouriendoc/springrest:1.0-SNAPSHOT`
 
### Integeration Tests
Semaphore is used to perform automatic continous integration [LINK](https://semaphoreci.com/shourien/springrest). The project is verfied for integrity using the Spring Boot Tester with Integration Tests written to ensure E2E working for each of the REST API.

### Application Guide
 
 * The landing page for this application is the Swagger based 2.0 UI. ![Landing Page](https://i.imgur.com/3ThZo4N.png, "Landing Page")
 
 * Rest resources exposed to the user for invoking API's. The corresponding documentation detailing the request/response and the ability to invoke the apis is also provided by Swagger seamlessly. ![API Listing](https://i.imgur.com/TTCGUIH.png, "Rest Resource Listing")
 
 * Following is an example to invoke the REST API below. This is the REST API to obtain details of the customer for a particular customer ID. ![Invoke API](https://i.imgur.com/HHPDYNA.png, "Rest API to fetch customer details") 
 
* The REST response of the API as provided by Swagger. ![REST Response](https://i.imgur.com/LaDtmJV.png, "Rest API response to obtaining customer details")
