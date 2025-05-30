FROM openjdk:21-slim
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 8080

ENV MYSQL_DATABASE=travelmanager_db
ENV MYSQL_HOST=travelmanager-db
ENV MYSQL_PASSWORD=secret123
ENV MYSQL_USER=tm_user

ENTRYPOINT ["java", "-jar", "app.jar"]
