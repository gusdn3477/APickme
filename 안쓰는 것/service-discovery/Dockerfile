FROM openjdk:11.0.12-jre
RUN ["mkdir","/myapp"]
WORKDIR /myapp
COPY ./target/service-discovery-1.0.jar ./service-discovery-1.0.jar
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "service-discovery-1.0.jar"]