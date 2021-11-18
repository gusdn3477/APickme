FROM openjdk:11.0.12-jre
VOLUME /tmp
COPY ./target/order-service-1.0.jar order-service-1.0.jar
ENTRYPOINT ["java", "-jar", "order-service-1.0.jar"]