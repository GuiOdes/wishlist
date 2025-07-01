# Wishlist API

A RESTful API for managing user wishlists. This application allows users to create wishlists, add and remove products, check if a product exists in their wishlist, and retrieve their wishlist.

## Technologies

- Java 21
- Spring Boot 3.5.3
- MongoDB
- Gradle
- JUnit 5
- Cucumber
- Testcontainers

## Features

- Create a wishlist for a user
- Add products to a wishlist
- Remove products from a wishlist
- Check if a product exists in a wishlist
- Retrieve a user's wishlist
- Limit the maximum number of products in a wishlist (default: 20, configurable)
- Prevent duplicate products in a wishlist

## Project Structure

The project follows a clean architecture pattern with the following layers:

- **Domain**: Contains the core business logic and entities
- **Application**: Contains use cases that orchestrate the business logic
- **Infrastructure**: Contains adapters for external systems (MongoDB, REST API)

## API Endpoints

### Add a Product to Wishlist

```
POST /api/v1/wishlist
```

Request body:
```json
{
   "userId": "uuid",
   "productId": "uuid"
}
```

### Find Wishlist by User ID

```
GET /api/v1/wishlist/{userId}
```

### Check if Product Exists in Wishlist

```
GET /api/v1/wishlist/{userId}/product/{productId}/exists
```

### Remove Product from Wishlist

```
DELETE /api/v1/wishlist/{userId}/product/{productId}
```

## Business Rules

- A user can have at most 20 products in their wishlist (configurable via `WISHLIST_ITEMS_MAX_SIZE` environment variable)
- A product cannot be added to a wishlist if it already exists in the wishlist
- If a wishlist doesn't exist when finding it, an empty wishlist is created
- Deleting a product from a non-existent wishlist or an empty wishlist doesn't return an error

## Setup and Installation

### Prerequisites

- Java 21
- Docker and Docker Compose (for MongoDB)

### Running the Application

1. Clone the repository
2. Start MongoDB using Docker Compose:
   ```
   docker-compose up -d
   ```
3. Build and run the application:
   ```
   ./gradlew bootRun
   ```

### Configuration

The application can be configured using the following environment variables:

- `WISHLIST_ITEMS_MAX_SIZE`: Maximum number of items allowed in a wishlist (default: 20)

## Testing

The project includes unit tests, integration tests, and BDD tests using Cucumber.

### Running Tests

```
./gradlew test
```

### Test Coverage

The project uses JaCoCo for test coverage reporting. To generate a coverage report:

```
./gradlew jacocoTestReport
```

The report will be available in `build/reports/jacoco/test/html/index.html`.

## Development

The project uses Testcontainers for integration testing with MongoDB, which automatically starts a MongoDB container during tests.