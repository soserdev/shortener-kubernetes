# https://snyk.io/blog/best-practices-to-build-java-containers-with-docker/
# 5. Don’t run containers as root
# jumper-api % docker build -t somnidev/jumper-api:latest -t somnidev/jumper-api:0.1 -f Dockerfile .
# FROM maven:3.8.1-jdk-11-slim@sha256:4ad59ff72c24f8444dceb7ae087832a3f4f3241e57ed4d98c2490b1d7e94efb2 AS build
FROM maven:3.8.1-jdk-11-slim AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean package -DskipTests


# FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine@sha256:a5f72791e32e1d3493f602bde395508147e35e42669bd4a014cc3b87a9f918a6
FROM openjdk:11.0.11-9-jre-slim
RUN mkdir /app
# RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
COPY --from=build /project/target/jumper-api-0.0.1-SNAPSHOT.jar /app/java-application.jar
WORKDIR /app
# RUN chown -R javauser:javauser /app
# USER javauser
CMD "java" "-jar" "java-application.jar"