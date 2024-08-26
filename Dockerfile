# Utiliser une image de base Java
FROM openjdk:17-jdk-alpine

# Ajouter un argument pour le nom du JAR
ARG JAR_FILE=target/*.jar

# Copier le JAR dans l'image Docker
COPY ${JAR_FILE} app.jar

# Exposer le port 8080 (ou celui sur lequel votre application écoute)
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "/app.jar"]
