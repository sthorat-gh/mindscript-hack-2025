# Docker Configuration

This directory contains Docker-related configuration files for the Prompt Repository backend.

## Files

- `docker-compose.yml` - Docker Compose configuration for running PostgreSQL
- `create-database.sql` - SQL script to create the database
- `docker.sh` - Convenience script for Docker operations

## Usage

### Using the convenience script

From the project root directory:
```bash
./docker/docker.sh start    # Start PostgreSQL
./docker/docker.sh stop     # Stop PostgreSQL
./docker/docker.sh restart  # Restart PostgreSQL
./docker/docker.sh clean    # Stop and remove all data
./docker/docker.sh logs     # View logs
./docker/docker.sh status   # Check status
```

### Using docker-compose directly

From this directory:
```bash
docker-compose up -d        # Start PostgreSQL
docker-compose down         # Stop PostgreSQL
docker-compose down -v      # Stop and remove all data
docker-compose logs -f      # View logs
docker-compose ps          # Check status
```

### Create database manually
If you have PostgreSQL installed locally:
```bash
psql -U postgres -f create-database.sql
```

## Default Configuration

- **Database**: promptrepo
- **Username**: postgres
- **Password**: postgres
- **Port**: 5432

These values can be customized in the `docker-compose.yml` file.
