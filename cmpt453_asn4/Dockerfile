# Use a Flutter image to build the web app
FROM cirrusci/flutter:stable AS build

# Set the working directory
WORKDIR /app

# Copy the Flutter project files to the container
COPY . .

# Build the Flutter web application
RUN flutter build web

# Use Nginx to serve the built web app
FROM nginx:alpine

# Copy the built web app to the Nginx HTML directory
COPY --from=build /app/build/web /usr/share/nginx/html

# Expose the Nginx port
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
