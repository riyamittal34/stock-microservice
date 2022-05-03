FROM openjdk:11.0.12
ADD ./target/stock-0.0.1-SNAPSHOT.jar stock-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "stock-0.0.1-SNAPSHOT.jar"]
EXPOSE 8086