FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/inventory-0.0.1-SNAPSHOT.jar inventory.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "inventory.jar"]