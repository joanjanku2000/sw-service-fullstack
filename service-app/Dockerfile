FROM maven:3.9.9-amazoncorretto-21

WORKDIR /service-app
COPY . .
RUN mvn clean install -DskipTests=true

CMD mvn spring-boot:run