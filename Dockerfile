FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]


# # ---------- Build Stage ----------
# FROM eclipse-temurin:21-jdk-jammy AS build

# WORKDIR /app

# # Copy build files first (to leverage Docker cache)
# COPY pom.xml mvnw ./
# COPY .mvn .mvn

# # Download dependencies
# RUN ./mvnw dependency:go-offline

# # Copy project source
# COPY src src

# # Package the application
# RUN ./mvnw clean package -DskipTests


# # ---------- Runtime Stage ----------
# FROM eclipse-temurin:21-jre-jammy

# WORKDIR /app

# # Copy the built jar from build stage
# COPY --from=build /app/target/*.jar app.jar

# EXPOSE 8080

# CMD ["java", "-jar", "app.jar"]
