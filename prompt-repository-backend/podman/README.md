# Container Configuration (Podman)

This directory contains container configuration files for the Prompt Repository backend using Podman.

## Files

- `podman-compose.yml` - Compose configuration for running PostgreSQL with Podman
- `create-database.sql` - SQL script to create the database
- `container.sh` - One-stop script that auto-installs Podman and manages containers
- `PODMAN_SETUP.md` - Manual installation instructions for Podman

## Usage

### Using the One-Stop Script

The `container.sh` script handles everything automatically, including installing Podman if it's not present.

From the project root directory:
```bash
./podman/container.sh start    # Install Podman (if needed) and start PostgreSQL
./podman/container.sh stop     # Stop PostgreSQL
./podman/container.sh restart  # Restart PostgreSQL
./podman/container.sh clean    # Stop and remove all data
./podman/container.sh logs     # View logs
./podman/container.sh status   # Check status
./podman/container.sh setup    # Just install/setup Podman without starting containers
```

**First-time users**: Just run `./podman/container.sh start` and the script will handle everything!

### Using podman-compose directly

From this directory:
```bash
podman-compose up -d        # Start PostgreSQL
podman-compose down         # Stop PostgreSQL
podman-compose down -v      # Stop and remove all data
podman-compose logs -f      # View logs
podman-compose ps          # Check status
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

These values can be customized in the `podman-compose.yml` file.
