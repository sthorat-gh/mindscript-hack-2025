#!/bin/bash

# Convenience script for Docker Compose commands

# Get the directory where this script is located
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
COMPOSE_FILE="$SCRIPT_DIR/docker-compose.yml"

case "$1" in
  start)
    echo "Starting PostgreSQL..."
    docker-compose -f $COMPOSE_FILE up -d
    ;;
  stop)
    echo "Stopping PostgreSQL..."
    docker-compose -f $COMPOSE_FILE down
    ;;
  restart)
    echo "Restarting PostgreSQL..."
    docker-compose -f $COMPOSE_FILE down
    docker-compose -f $COMPOSE_FILE up -d
    ;;
  clean)
    echo "Stopping PostgreSQL and removing volumes..."
    docker-compose -f $COMPOSE_FILE down -v
    ;;
  logs)
    docker-compose -f $COMPOSE_FILE logs -f
    ;;
  status)
    docker-compose -f $COMPOSE_FILE ps
    ;;
  *)
    echo "Usage: $0 {start|stop|restart|clean|logs|status}"
    echo ""
    echo "  start   - Start PostgreSQL container"
    echo "  stop    - Stop PostgreSQL container"
    echo "  restart - Restart PostgreSQL container"
    echo "  clean   - Stop container and remove volumes"
    echo "  logs    - Show container logs"
    echo "  status  - Show container status"
    exit 1
esac
