# Étape 1 : Construire le projet avec Maven
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

# Étape 2 : Créer l'image finale avec OpenJDK 17
FROM openjdk:17-oracle

VOLUME /tmp
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
