# electric-vehicle-charging-station-management-system ![build](https://github.com/rezaep/electric-vehicle-charging-station-management-system/workflows/build/badge.svg) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=rezaep_electric-vehicle-charging-station-management-system&metric=alert_status)](https://sonarcloud.io/dashboard?id=rezaep_electric-vehicle-charging-station-management-system) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=rezaep_electric-vehicle-charging-station-management-system&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=rezaep_electric-vehicle-charging-station-management-system) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=rezaep_electric-vehicle-charging-station-management-system&metric=coverage)](https://sonarcloud.io/dashboard?id=rezaep_electric-vehicle-charging-station-management-system)

A simple project based on Java 11, Spring Boot, H2, Hibernate Spatial, [NestedJ](https://github.com/eXsio/nestedj), and JUnit 5.

## Getting Started

These instructions will get you a copy of the project and run on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to run the project:

```
Java Development Kit (JDK 11 or newer)
Maven
Docker (if you want to run the application using the Docker image)
```

### Installing

Clone the repository:

```
git clone https://github.com/rezaep/electric-vehicle-charging-station-management-system
```

Use Maven build tool to compile and build the project:

```
mvn clean compile
```
### Running the tests

#### Unit tests

To run unit tests use the following command:

```
mvn test
```
### Running the application

#### Run using Java

To run the application using Java, run the following command:

```
java -jar target/evcsms-1.0.0.jar
```

#### Run using Maven and Spring Boot plugin

To run the application using Spring boot maven-plugin, run the following command:
                                                        
```
mvn spring-boot:run
```

#### Run using Docker

To package the Jar file inside a Docker image, use the following commands:

```
mvn clean package
docker build -t image:tag . (e.g. subliz/evcsms:latest)
```

To run the application using Docker, run the following command:
                                                        
```
docker run image:tag (e.g. subliz/evcsms:latest)
```

#### Run using Docker Compose

To run the application using Docker Compose, run the following command:
                                                        
```
docker-compose up -d
```

#### Start working with APIs
Swagger generates documentation of REST APIs, which is accessible using this [link](http://localhost:8080/swagger-ui.html).

## Authors

* **Reza Ebrahimpour** - [Github](https://github.com/rezaep)

## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details