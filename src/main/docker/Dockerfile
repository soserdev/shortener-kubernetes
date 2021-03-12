FROM openjdk:13-jdk-alpine
MAINTAINER Simon Oser <simon.oser@gmail.com>

# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/myservice/myservice.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/myservice/myservice.jar"]
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /usr/share/myservice/myservice.jar"]

###


FROM openjdk:13-jdk-alpine

VOLUME /tmp
COPY /@project.build.finalName@.jar /opt/spring-boot/
RUN sh -c 'touch /${fileName}.jar'
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /opt/spring-boot/@project.build.finalName@.jar"]

##
FROM openjdk:14-jdk-alpine

