FROM node:14

# Définit le répertoire de travail à /app
WORKDIR /app

# Copie le package.json et package-lock.json
COPY package*.json ./

# Installe les dépendances
RUN npm install

# Copie tous les fichiers du projet dans le conteneur
COPY . .

# Expose le port sur lequel l'application va écouter
EXPOSE 3000

# Démarre l'application
CMD ["npm", "start"]
