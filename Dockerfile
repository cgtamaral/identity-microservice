FROM openjdk:8
ADD IdentityMicroService.jar IdentityMicroService.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar", "IdentityMicroService.jar"]
