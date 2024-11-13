FROM maven:3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM maven:3.8.4-openjdk-17-slim
WORKDIR /app
COPY --from=build /app/target/worldline-0.0.1-SNAPSHOT.jar /app/worldline.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","worldline.jar"]