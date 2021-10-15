FROM openjdk:11.0.12-jre
VOLUME /tmp
COPY ./target/catalog-service-1.0.jar ./catalog-service-1.0.jar

ENTRYPOINT ["java", "-jar", "catalog-service-1.0.jar"]