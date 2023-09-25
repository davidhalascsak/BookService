FROM maven:3.8.5-openjdk-17 AS build
COPY . /app
WORKDIR /app
RUN mvn package -DskipTests

FROM openjdk:17-ea-slim
COPY --from=build /app/target/BookService-0.0.1.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]