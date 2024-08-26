# Utilise une image officielle Node.js
FROM node:14

# Définit le répertoire de travail
WORKDIR /app

# Copie le package.json et installe les dépendances
COPY package*.json ./
RUN npm install

# Copie tout le reste du projet
COPY . .

# Expose le port de l'application
EXPOSE 3000

# Commande pour démarrer l'application
CMD ["npm", "start"]
