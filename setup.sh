#!/bin/bash

set -e


REPO_URL="https://github.com/joanjanku2000/sw-service-fullstack.git"

if [ -d "sw-service-fullstack" ]; then
  echo "Directory exists"
  rm -rf sw-service-fullstack
fi

echo "Cloning repository..."

git clone "$REPO_URL"

cd sw-service-fullstack

install_docker() {
  echo "Docker not found. Installing Docker..."

  sudo apt-get update
  sudo apt-get install -y \
    ca-certificates \
    curl \
    gnupg \
    lsb-release

  sudo mkdir -p /etc/apt/keyrings
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | \
    sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
  echo \
    "deb [arch=$(dpkg --print-architecture) \
    signed-by=/etc/apt/keyrings/docker.gpg] \
    https://download.docker.com/linux/ubuntu \
    $(lsb_release -cs) stable" | \
    sudo tee /etc/apt/sources.list.d/docker.list > /dev/null


  sudo apt-get update
  sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

  echo "Docker installation complete."
}

install_docker_compose() {
  echo "Docker Compose not found. Installing Docker Compose..."

  COMPOSE_VERSION="v2.27.0"
  sudo curl -L "https://github.com/docker/compose/releases/download/${COMPOSE_VERSION}/docker-compose-$(uname -s)-$(uname -m)" \
    -o /usr/local/bin/docker-compose
  sudo chmod +x /usr/local/bin/docker-compose

  echo "Docker Compose installation finished"
}


if ! command -v docker &> /dev/null; then
  install_docker
else
  echo "Docker is already installed "
fi

if ! docker compose version &> /dev/null && ! command -v docker-compose &> /dev/null; then
  install_docker_compose
else
  echo "Docker Compose is already installed "
  docker compose down
fi
echo "Starting Docker Compose with build..."
docker compose up --build -d
