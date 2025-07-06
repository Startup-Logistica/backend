FROM maven:3.9.9-amazoncorretto-23 AS build

WORKDIR /app

COPY pom.xml .
COPY . .

COPY src ./src

RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:23-jdk-slim

WORKDIR /app

COPY --from=build /app/target/defaultproject-0.0.1-SNAPSHOT.jar /app/app.jar

ENV PROFILE=dev
ENV PORT=$(PORT)
ENV TZ="America/Sao_Paulo"

EXPOSE $PORT

CMD ["java", "-jar", "app.jar"]