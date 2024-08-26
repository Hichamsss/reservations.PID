# Utiliser une image de base contenant OpenJDK
FROM openjdk:17-jdk-alpine

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR généré dans le conteneur
COPY target/reservationsSpringBoot-0.0.1-SNAPSHOT.jar /app/reservations.jar

# Exposer le port 8080
EXPOSE 8080

# Démarrer l'application
CMD ["java", "-jar", "/app/reservations.jar"]
