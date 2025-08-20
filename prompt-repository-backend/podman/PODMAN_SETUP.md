# Setting Up Podman

Podman is a daemonless container engine for developing, managing, and running OCI Containers. It doesn't require a daemon running in the background and can run containers as a regular user.

## Installation

### macOS
```bash
# Using Homebrew
brew install podman
brew install podman-compose

# Initialize and start Podman machine
podman machine init
podman machine start
```

### Linux (Ubuntu/Debian)
```bash
# Install Podman
sudo apt-get update
sudo apt-get install -y podman

# Install podman-compose
pip3 install podman-compose
```

### Linux (Fedora/RHEL/CentOS)
```bash
# Install Podman
sudo dnf install -y podman

# Install podman-compose
pip3 install podman-compose
```

## Verify Installation
```bash
podman --version
podman-compose --version
```

## Key Features

1. **No Daemon**: Podman runs without a background daemon
2. **Rootless**: Can run containers without root privileges
3. **OCI Compliant**: Uses industry-standard container formats
4. **Systemd Integration**: Better integration with systemd on Linux
5. **Security**: Enhanced security through rootless containers and user namespaces

## Using with This Project

The `container.sh` script uses Podman to manage the PostgreSQL container. You can also use podman-compose directly:

```bash
# Start PostgreSQL
podman-compose -f podman-compose.yml up -d

# Stop PostgreSQL
podman-compose -f podman-compose.yml down
```

## Troubleshooting

### On macOS
If you get connection errors:
```bash
# Check if the Podman machine is running
podman machine list

# Start it if needed
podman machine start
```

### Permission Issues
If you encounter permission issues with volumes:
```bash
# Run with proper user namespace
podman run --userns=keep-id ...
```

### Port Conflicts
If you get "bind: address already in use" errors:
```bash
# Check what's using the port (e.g., 5432 for PostgreSQL)
lsof -i :5432

# If Docker is using the port, stop the container
docker ps
docker stop <container-name>

# Or change the port in podman-compose.yml
```

## Additional Resources

- [Podman Documentation](https://docs.podman.io/)
- [Podman Compose Documentation](https://github.com/containers/podman-compose)
- [Migrating from Docker to Podman](https://www.redhat.com/sysadmin/podman-docker-compose)
