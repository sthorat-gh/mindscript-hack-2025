#!/bin/bash

# One-stop convenience script for Podman container management
# Handles installation, setup, and container operations

set -e  # Exit on error

# Get the directory where this script is located
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
COMPOSE_FILE="$SCRIPT_DIR/podman-compose.yml"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

# Detect OS
detect_os() {
    if [[ "$OSTYPE" == "darwin"* ]]; then
        echo "macos"
    elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
        if [ -f /etc/debian_version ]; then
            echo "debian"
        elif [ -f /etc/redhat-release ]; then
            echo "redhat"
        else
            echo "linux"
        fi
    else
        echo "unknown"
    fi
}

# Install Podman based on OS
install_podman() {
    local os=$(detect_os)
    
    print_status "Installing Podman for $os..."
    
    case "$os" in
        macos)
            if ! command -v brew &> /dev/null; then
                print_error "Homebrew is required to install Podman on macOS"
                print_status "Please install Homebrew from https://brew.sh"
                exit 1
            fi
            brew install podman
            ;;
        debian)
            print_status "Installing Podman on Debian/Ubuntu..."
            sudo apt-get update
            sudo apt-get install -y podman
            ;;
        redhat)
            print_status "Installing Podman on Fedora/RHEL/CentOS..."
            sudo dnf install -y podman
            ;;
        *)
            print_error "Unsupported OS for automatic installation"
            print_status "Please install Podman manually"
            exit 1
            ;;
    esac
}

# Install podman-compose
install_podman_compose() {
    local os=$(detect_os)
    
    print_status "Installing podman-compose..."
    
    if [[ "$os" == "macos" ]]; then
        if command -v brew &> /dev/null; then
            brew install podman-compose
        fi
    else
        # For Linux, use pip3
        if command -v pip3 &> /dev/null; then
            pip3 install podman-compose
        elif command -v python3 &> /dev/null; then
            print_status "Installing pip3 first..."
            if [[ "$os" == "debian" ]]; then
                sudo apt-get install -y python3-pip
            elif [[ "$os" == "redhat" ]]; then
                sudo dnf install -y python3-pip
            fi
            pip3 install podman-compose
        else
            print_error "Python 3 and pip3 are required for podman-compose"
            exit 1
        fi
    fi
}

# Setup Podman machine on macOS
setup_podman_machine() {
    if [[ "$(detect_os)" != "macos" ]]; then
        return 0
    fi
    
    # Check if any podman machine exists
    if ! podman machine list | grep -q "podman-machine-default"; then
        print_status "Creating Podman machine..."
        podman machine init
    fi
    
    # Check if machine is running
    machine_status=$(podman machine list --format "{{.Name}},{{.Running}}" | grep "podman-machine-default" || echo "")
    
    if [[ -z "$machine_status" ]]; then
        print_error "No podman machine found"
        return 1
    elif [[ "$machine_status" == *",true" ]]; then
        print_status "Podman machine is already running"
    else
        print_status "Starting Podman machine..."
        podman machine start
    fi
}

# Main setup function
ensure_podman_ready() {
    # Check if podman is installed
    if ! command -v podman &> /dev/null; then
        print_warning "Podman not found. Installing..."
        install_podman
    else
        print_status "Podman is already installed: $(podman --version)"
    fi
    
    # Check if podman-compose is installed
    if ! command -v podman-compose &> /dev/null; then
        print_warning "podman-compose not found. Installing..."
        install_podman_compose
    else
        print_status "podman-compose is already installed"
    fi
    
    # Setup podman machine on macOS
    if [[ "$(detect_os)" == "macos" ]]; then
        setup_podman_machine
    fi
    
    # Verify everything is working
    if ! podman version &> /dev/null; then
        print_error "Podman is not working correctly"
        exit 1
    fi
    
    print_status "Podman is ready!"
}

# Container operations
case "$1" in
  start)
    ensure_podman_ready
    print_status "Starting PostgreSQL..."
    podman-compose -f $COMPOSE_FILE up -d
    if [ $? -eq 0 ]; then
        print_status "PostgreSQL started successfully"
        print_status "Connection info: postgres://postgres:postgres@localhost:5432/promptrepo"
    fi
    ;;
  stop)
    ensure_podman_ready
    print_status "Stopping PostgreSQL..."
    podman-compose -f $COMPOSE_FILE down
    ;;
  restart)
    ensure_podman_ready
    print_status "Restarting PostgreSQL..."
    podman-compose -f $COMPOSE_FILE down
    podman-compose -f $COMPOSE_FILE up -d
    ;;
  clean)
    ensure_podman_ready
    print_status "Stopping PostgreSQL and removing volumes..."
    podman-compose -f $COMPOSE_FILE down -v
    ;;
  logs)
    ensure_podman_ready
    podman-compose -f $COMPOSE_FILE logs -f
    ;;
  status)
    ensure_podman_ready
    print_status "Checking container status..."
    podman-compose -f $COMPOSE_FILE ps || true
    ;;
  setup)
    # Just run setup without any container operations
    ensure_podman_ready
    print_status "Setup completed. You can now use './container.sh start' to run PostgreSQL"
    ;;
  *)
    echo "Usage: $0 {start|stop|restart|clean|logs|status|setup}"
    echo ""
    echo "  start   - Install Podman if needed, setup, and start PostgreSQL"
    echo "  stop    - Stop PostgreSQL container"
    echo "  restart - Restart PostgreSQL container"
    echo "  clean   - Stop container and remove volumes"
    echo "  logs    - Show container logs"
    echo "  status  - Show container status"
    echo "  setup   - Install and setup Podman without starting containers"
    echo ""
    echo "This script will automatically:"
    echo "  - Install Podman and podman-compose if not present"
    echo "  - Setup Podman machine on macOS"
    echo "  - Start/manage PostgreSQL container"
    exit 1
esac