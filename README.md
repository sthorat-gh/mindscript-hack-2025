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

## Prerequisites

The project includes an automated setup script that handles Podman installation. However, if you prefer manual installation:

### Installing Podman Manually

#### macOS
```bash
# Using Homebrew
brew install podman podman-compose

# Initialize and start Podman machine (required on macOS)
podman machine init
podman machine start

# Verify the machine is running
podman machine list
```

#### Linux (Ubuntu/Debian)
```bash
# Install Podman
sudo apt-get update
sudo apt-get install -y podman

# Install podman-compose
pip3 install podman-compose
```

#### Linux (Fedora/RHEL/CentOS)
```bash
# Install Podman
sudo dnf install -y podman

# Install podman-compose
pip3 install podman-compose
```

#### Verify Installation
```bash
podman --version
podman-compose --version
```

For more detailed setup instructions, see [podman/PODMAN_SETUP.md](prompt-repository-backend/podman/PODMAN_SETUP.md).

## Quick Start

1. Navigate to the backend directory:
   ```bash
   cd prompt-repository-backend
   ```

2. Start PostgreSQL (automatically installs Podman if needed):
   ```bash
   ./podman/container.sh start
   ```
   
   The script will automatically:
   - Install Podman and podman-compose if not present
   - Initialize and start Podman machine (on macOS)
   - Start the PostgreSQL container

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
- **Containerization**: Podman
- **Build Tool**: Gradle
- **API Documentation**: SpringDoc OpenAPI (Swagger)

For detailed backend setup and API documentation, see the [backend README](prompt-repository-backend/README.md).