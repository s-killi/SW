FROM openjdk:21-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY target/localcommuntiy-0.0.1-SNAPSHOT.jar sw.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar sw.jar"]
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar sw.jar"]
