FROM openjdk:11
VOLUME /tmp
COPY target/apibooks-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Xmx512m", "-Dspring.profiles.active=container", "-jar", "/app.jar"]

