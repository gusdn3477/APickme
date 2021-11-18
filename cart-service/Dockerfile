FROM openjdk:11.0.12-jre
VOLUME /tmp
COPY ./target/cart-service-1.0.jar cart-service-1.0.jar
ENTRYPOINT ["java", "-jar", "cart-service-1.0.jar"]