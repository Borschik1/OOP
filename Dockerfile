FROM maven:3.8.5-openjdk-18-slim AS build

COPY src /app/src
COPY pom.xml /app

RUN mvn -B test -f /app/pom.xml clean package

FROM eclipse-temurin:18.0.2_9-jre-alpine

COPY --from=build /app/target/Telegram_bot-1.0-SNAPSHOT-jar-with-dependencies.jar /app/bot.jar
ENTRYPOINT ["java", "-jar", "/app/bot.jar"]
CMD ["/app/config.properties"]