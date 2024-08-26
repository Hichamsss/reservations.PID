# Étape 1 : Construire l'application
FROM maven:3.8.7-openjdk-17 AS build
WORKDIR /app

# Copier le fichier pom.xml et télécharger les dépendances de Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copier le reste du code source et compiler l'application
COPY src ./src
RUN mvn package -DskipTests

# Étape 2 : Créer l'image finale
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Exposer le port
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.ja
