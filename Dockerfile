# Example
FROM maven:3-amazoncorretto-23

WORKDIR /bezkoder-app
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run