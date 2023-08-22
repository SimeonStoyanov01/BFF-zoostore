# Use a base image with Java 17 support
FROM openjdk:17


# Set the working directory inside the container
WORKDIR /zoobffapp

# Copy the .jar file from the local directory to the container
COPY target/tinqin-bff.jar /zoobffapp/

# Command to run when the container starts
CMD ["java", "-jar", "/zoobffapp/tinqin-bff.jar"]

EXPOSE 8082
