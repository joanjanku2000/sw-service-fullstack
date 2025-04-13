#!/bin/bash

# Start Mongo container
docker compose up mongo_db -d

# kills underlying processes
cleanup() {
  echo "Cleaning up..."
  if [[ -n "$SPRING_PID" ]]; then
    kill "$SPRING_PID" 2>/dev/null && echo "Terminated Spring Boot (PID: $SPRING_PID)"
  fi
  if [[ -n "$NG_SERVE_PID" ]]; then
    kill "$NG_SERVE_PID" 2>/dev/null && echo "Terminated Angular (PID: $NG_SERVE_PID)"
  fi

  docker compose down mongo_db
  exit 0
}

# Trap (Ctrl+C), TERM, and EXIT signals
trap cleanup INT TERM EXIT

cd service-app
mvn clean install -DskipTests=true
mvn spring-boot:run &
SPRING_PID=$!
cd ..

cd service-app-fe
ng serve &
NG_SERVE_PID=$!

wait $SPRING_PID
wait $NG_SERVE_PID
