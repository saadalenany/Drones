# Drones

### Prerequisites

In order to run the project, you'll need to have the following installed on your machine
* JDK 8 or above
* Maven 3.3+
* Git tool suitable for your platform


### Project Description
The project uses H2 database located & automatically created in /data directory.
The project logs in events, transactions & scheduled jobs in daily generated logs in /data/logs directory.

#### Used APIs
* Spring Boot Data JPA
* Spring Boot Web
* Spring Boot Thymeleaf
* Validation
* Lombok

### Downloading

Clone the repository to your local using following command
```
git clone <<repoURL>>
```


### Deployment

Navigate to the root of the project via command line and execute the following command
```
mvn spring-boot:run
```
The CLI will run your application on the configured port in the application.properties file.
