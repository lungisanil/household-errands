#
# Build stage
#
FROM maven:3.9-amazoncorretto-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

#
# Run stage
#
FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/target/household-errands-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

#docker build -t hazelcast-app-image .
#docker run -p 8080:8080 --name hazelcast-app-image-container hazelcast-app-image