FROM openjdk:23

WORKDIR /usr/local/easytownhallBackend
COPY target/EasyTownHall-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
