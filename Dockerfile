# Étape 1 : Construire le JAR avec Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copier les fichiers nécessaires pour le build Maven
COPY pom.xml .
COPY src ./src

# Exécuter la commande Maven pour construire le JAR
RUN mvn clean package -DskipTests

# Étape 2 : Créer l'image Docker à partir du JAR
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copier le fichier JAR généré de l'étape de build précédente
COPY --from=build /app/target/reservationsSpringBoot-0.0.1-SNAPSHOT.jar /app/reservations.jar

# Exposer le port 8080
EXPOSE 8080

# Démarrer l'application
CMD ["java", "-jar", "/app/reservations.jar"]
