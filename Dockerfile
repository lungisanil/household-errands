#
# Build stage
#
FROM maven:3.8.2-jdk-8 AS build
COPY . .
RUN mvn clean package -Pprod

FROM openjdk:8
COPY --from=build /HouseHoldErrandsWeb/target/HouseHoldErrandsWeb-1.0.0-SNAPSHOT.war HouseHoldErrandsWeb.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/HouseHoldErrandsWeb.war"]

#docker build -t hazelcast-app-image .
#docker run -p 8080:8080 --name hazelcast-app-image-container hazelcast-app-image