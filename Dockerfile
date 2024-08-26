# Utiliser une image de base Java
FROM openjdk:17-jdk-alpine

# Créer un répertoire pour l'application
WORKDIR /app

# Copier le fichier JAR dans le conteneur
COPY target/reservationsSpringBoot-0.0.1-SNAPSHOT.jar /app/reservations.jar

# Exposer le port 8080
EXPOSE 8080

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "/app/reservations.jar"]
