FROM dart:stable

# Set the working directory
WORKDIR /app

# Copy pubspec files first and resolve dependencies
COPY pubspec.* ./
RUN dart pub get

# Copy source files separately to trigger only relevant rebuilds
COPY . .

# Expose the server port
EXPOSE 8080

# Set the command to start the server
CMD ["dart", "bin/server.dart"]
