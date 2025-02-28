FROM openjdk:21-jdk

COPY target/ecommerce-app.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "ecommerce-app.jar"]
