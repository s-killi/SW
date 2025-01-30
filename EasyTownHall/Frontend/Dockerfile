# Nutze Node.js 23.3.0 für den Build
FROM node:23.3.0 AS build

# Arbeitsverzeichnis im Container erstellen
WORKDIR /usr/src/app

# Kopiere die Projektdateien in das Arbeitsverzeichnis
COPY . .

# Installiere die Abhängigkeiten
RUN npm install

# Baue die Angular-Anwendung für die Produktion
RUN npm run build -- --configuration=production

# Nutze NGINX für die Bereitstellung
FROM nginx:stable-alpine

# Entferne die Standard-NGINX-Konfiguration
RUN rm /etc/nginx/conf.d/default.conf

# Kopiere die benutzerdefinierte NGINX-Konfiguration
COPY nginx.conf /etc/nginx/conf.d

# Kopiere die gebauten Dateien in das NGINX-HTML-Verzeichnis
COPY --from=build /usr/src/app/dist/easy-town-hall-frontend/browser /usr/share/nginx/html

# Exponiere den Port
EXPOSE 80

# Starte NGINX
CMD ["nginx", "-g", "daemon off;"]
