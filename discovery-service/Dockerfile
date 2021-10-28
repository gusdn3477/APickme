FROM openjdk:11.0.12-jre

RUN mkdir /server
WORKDIR /server

COPY ./target/discovery-service-1.0.jar ./discovery-service-1.0.jar
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "./discovery-service-1.0.jar"]
