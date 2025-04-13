#!/bin/bash

# Start Mongo container
docker compose up mongo_db -d
docker build -f service-app-fe/Dockerfile.local -t fe-app .
docker compose run -e SPRING_PROFILES_ACTIVE=local -p 8181:8080 -d app
docker run --network=service-app_sw-network -p 4200:4200 -d fe-app


