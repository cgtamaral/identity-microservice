FROM java:8
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} IdentityMicroService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/IdentityMicroService-0.0.1-SNAPSHOT.jar"]
EXPOSE 8086
CMD java - jar IdentityMicroService-0.0.1-SNAPSHOT.jar