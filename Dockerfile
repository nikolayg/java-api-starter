FROM eclipse-temurin:17 as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the code after installing dependencies - Note! .dockerignore
COPY . .

# Run the Gradle build to compile the app
RUN ./gradlew clean build

# Stage 2: Build the actual container from the builder's output
FROM eclipse-temurin:17

# The port we're listening on
# Copy build bundle from the builder container
COPY --from=builder /app/app/build/libs/app.jar /app/

# Make sure we don't run as root
RUN chown -R nobody /app
USER nobody

CMD ["java", "-jar", "/app/app.jar"]