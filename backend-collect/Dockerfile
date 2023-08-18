FROM openjdk:8
EXPOSE 8080
ADD target/backend-collect.jar backend-collect.jar
ENTRYPOINT ["java","-jar","/backend-collect.jar"]