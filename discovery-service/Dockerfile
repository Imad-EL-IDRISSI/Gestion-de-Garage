FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app
# Copie uniquement les fichiers nécessaires pour la compilation
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2: Runtime
FROM openjdk:17-jdk-slim

WORKDIR /app
# Copier uniquement le JAR généré de l'étape 1
COPY --from=build /app/target/*.jar app.jar

# Commande pour exécuter l'application
CMD ["java", "-jar", "app.jar"]
