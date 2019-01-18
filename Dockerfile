FROM openjdk:8
ADD target/IdentityMicroService.jar IdentityMicroService.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar", "IdentityMicroService.jar"]
