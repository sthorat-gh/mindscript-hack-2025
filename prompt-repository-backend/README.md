# Prompt Repository Backend

A simple Spring Boot backend for managing prompts with CRUD operations.

## Features

- Create, Read, Update, Delete prompts
- Organize prompts with categories and tags
- Search prompts by title, content, or description
- Support for prompt variables
- PostgreSQL database for persistence
- Swagger UI for API documentation

## Requirements

- Java 17 or higher
- Gradle 7.x or higher
- PostgreSQL 12 or higher (or use Podman to run it in a container)
- Podman and podman-compose (for containerized PostgreSQL)

## Getting Started

1. Clone the repository
2. Navigate to the backend directory:
   ```bash
   cd prompt-repository-backend
   ```

3. Set up PostgreSQL database:
   
   **Option 1: Using the One-Stop Script (Recommended)**
   ```bash
   # This will install Podman if needed and start PostgreSQL
   ./podman/container.sh start
   ```
   
   The script automatically handles:
   - Installing Podman and podman-compose if not present
   - Setting up Podman machine on macOS
   - Starting the PostgreSQL container
   
   **Configure database credentials** (if using custom settings):
   ```bash
   export DB_URL=jdbc:postgresql://localhost:5432/promptrepo
   export DB_USERNAME=postgres
   export DB_PASSWORD=your_password
   ```

4. Run the application:
   ```bash
   ./gradlew bootRun
   ```

5. The application will start on http://localhost:8081

## API Documentation

Once the application is running, you can access:
- Swagger UI: http://localhost:8081/swagger-ui
- API Docs: http://localhost:8081/api-docs

## API Endpoints

### Prompts

- `GET /api/prompts` - Get all prompts (paginated)
  - Query params: page, size, sortBy, sortDirection
- `GET /api/prompts/{id}` - Get a specific prompt
- `GET /api/prompts/search?q={query}` - Search prompts
- `GET /api/prompts/category/{categoryId}` - Get prompts by category
- `GET /api/prompts/tag/{tag}` - Get prompts by tag
- `POST /api/prompts` - Create a new prompt
- `PUT /api/prompts/{id}` - Update a prompt
- `DELETE /api/prompts/{id}` - Delete a prompt

### Categories

- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get a specific category
- `POST /api/categories` - Create a new category
- `PUT /api/categories/{id}` - Update a category
- `DELETE /api/categories/{id}` - Delete a category

## Data Models

### Prompt
```json
{
  "id": "string",
  "title": "string",
  "content": "string",
  "description": "string",
  "categories": [Category],
  "tags": ["string"],
  "variables": [PromptVariable],
  "createdAt": "datetime",
  "updatedAt": "datetime"
}
```

### Category
```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "icon": "string",
  "color": "string",
  "displayOrder": 0,
  "promptCount": 0
}
```

### PromptVariable
```json
{
  "name": "string",
  "description": "string",
  "defaultValue": "string",
  "required": true,
  "type": "TEXT|NUMBER|BOOLEAN|SELECT|MULTILINE"
}
```

## Example Requests

### Create a Prompt
```bash
curl -X POST http://localhost:8081/api/prompts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Bug Detection Prompt",
    "content": "Find all potential bugs in the following code...",
    "description": "A prompt for detecting common bugs",
    "categoryIds": ["category-id-1"],
    "tags": ["debugging", "code-review"],
    "variables": [{
      "name": "codeLanguage",
      "description": "Programming language",
      "defaultValue": "JavaScript",
      "required": true,
      "type": "TEXT"
    }]
  }'
```

### Create a Category
```bash
curl -X POST http://localhost:8081/api/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Code Review",
    "description": "Prompts for code review",
    "icon": "🔍",
    "color": "#FF5733",
    "displayOrder": 1
  }'
```

## Development

The application uses PostgreSQL database. Make sure to have PostgreSQL installed and running with a database named `promptrepo` created. You can customize the database connection settings through environment variables or by updating the `application.yml` file.

### Running with Podman Compose

**Using the convenience script:**
```bash
# Start PostgreSQL
./podman/container.sh start

# Stop PostgreSQL
./podman/container.sh stop

# Restart PostgreSQL
./podman/container.sh restart

# Stop and remove all data
./podman/container.sh clean

# View logs
./podman/container.sh logs

# Check status
./podman/container.sh status
```
