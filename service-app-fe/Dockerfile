#  Build  App
FROM node:18-alpine as build

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build -- --configuration production

# Serve with http-server
FROM node:18-alpine

RUN npm install -g http-server

COPY --from=build /app/dist/frontend-app/browser .

EXPOSE 4200


CMD ["http-server", "-p", "4200"]
