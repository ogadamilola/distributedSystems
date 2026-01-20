# Use the Dart image to build and run the server
FROM dart:stable AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the project files to the container
COPY . .

# Resolve and fetch dependencies
RUN dart pub get

# Compile the server (optional if using `dart run`)
RUN dart compile exe bin/server.dart -o server

# Expose the port the server runs on (e.g., 8080)
EXPOSE 8080

# Run the server
CMD ["./server"]
