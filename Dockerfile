#FROM gradle:8.4-jdk17-alpine As builder
##ENV SPRING_PROFILES_ACTIVE=native
#WORKDIR /app
#COPY . .
#
## Build the application
#RUN gradle build --no-daemon
#EXPOSE 8080
#FROM openjdk:17
### Copy the jar file from the build/libs directory to the Docker image
#COPY --from=builder /app/build/libs/*-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
#
#
#
#
FROM gradle:8.4-jdk17-alpine  as build
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY gradle ./gradle
COPY src ./src
RUN gradle build --no-daemon

# Run stage
FROM openjdk:17 as runtime
WORKDIR /app
COPY --from=build /app/build/libs/*.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]