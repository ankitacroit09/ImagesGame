FROM openjdk:8-jre-alpine
ADD utilizationapp-0.0.1-SNAPSHOT.jar imagedemo.jar
ENTRYPOINT ["java","-Dserver.port=8080","-jar","/imagedemo.jar"]