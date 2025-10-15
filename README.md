# coc-data

A data synchronization service for Clash of Clans (COC) war information.

## Overview

This project provides automated synchronization of Clash of Clans war data, including war logs, attack records, and player statistics. It helps clans track their war performance and member participation.

## Features

- **War Data Sync**: Automatically synchronize war information from COC API
- **Attack Tracking**: Record and analyze attack attempts and results
- **Player Statistics**: Track individual player performance across wars
- **Notification System**: Send notifications for three-star attacks
- **Redis Caching**: Efficient data caching for improved performance

## Tech Stack

- **Spring Boot 2.3.x**: Application framework
- **MyBatis**: Database ORM
- **MySQL**: Data persistence
- **Redis**: Caching layer
- **WebFlux**: Reactive web client for API calls

## Requirements

- Java 8 or higher
- MySQL 5.7+
- Redis 3.2+
- Maven 3.6+

## Configuration

Copy `application.properties.example` to `application.properties` and configure:

```properties
# Database configuration
spring.datasource.url=jdbc:mysql://localhost:3306/coc_data
spring.datasource.username=your_username
spring.datasource.password=your_password

# Redis configuration
spring.redis.host=localhost
spring.redis.port=6379

# COC API configuration
coc.api.token=your_api_token
```

## Build & Run

```bash
# Build the project
mvn clean package

# Run the application
java -jar target/data-v1.jar
```

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

Sincerely thanks to JetBrains for their open source license support: https://www.jetbrains.com/community/opensource/#support

![jb_beam](https://user-images.githubusercontent.com/17401206/148912151-0c7b67c7-516b-4b83-bd1b-cab316e24e6d.png)
