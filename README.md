# Prompt Repository - MindScript Hack 2025

A central repository for hosting and managing prompts to be given to AI agents. This project provides a backend API for CRUD operations on prompts with categorization and tagging support.

## Project Structure

- `prompt-repository-backend/` - Spring Boot backend API

## Backend Features

- **CRUD Operations**: Create, Read, Update, Delete prompts
- **Categories**: Organize prompts into categories
- **Tags**: Add tags to prompts for better searchability
- **Variables**: Support for prompt templates with variables
- **Search**: Search prompts by title, content, or description
- **API Documentation**: Swagger UI for easy API exploration

## Quick Start

1. Navigate to the backend directory:
   ```bash
   cd prompt-repository-backend
   ```

2. Start PostgreSQL using Docker Compose:
   ```bash
   ./docker/docker.sh start
   # or
   docker-compose -f docker/docker-compose.yml up -d
   ```

3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

4. Access the API:
   - API: http://localhost:8081/api
   - Swagger UI: http://localhost:8081/swagger-ui
   - Health Check: http://localhost:8081/api/health

## Technology Stack

- **Backend**: Spring Boot 3.2, Java 17
- **Database**: PostgreSQL
- **Build Tool**: Gradle
- **API Documentation**: SpringDoc OpenAPI (Swagger)

For detailed backend setup and API documentation, see the [backend README](prompt-repository-backend/README.md).