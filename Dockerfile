FROM openjdk:17-oracle

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Set the name of the application's jar file
ENV JAR_FILE=room-allocation-0.0.1-SNAPSHOT.jar

# Set the path to the application's jar file
ARG JAR_PATH="build/libs/${JAR_FILE}"

# Copy the application's jar to the container
COPY ${JAR_PATH} ${JAR_FILE}

# Run the jar file
ENTRYPOINT exec java -jar ${JAR_FILE}
