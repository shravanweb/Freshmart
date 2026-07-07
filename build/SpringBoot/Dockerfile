FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY gradlew gradlew.bat settings.gradle.kts build.gradle.kts ./
COPY gradle ./gradle
COPY libs ./libs
COPY src ./src

RUN chmod +x gradlew && ./gradlew installDist --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/build/install/inventory.management.system ./

ENV SERVER_PORT=8080
EXPOSE 8080

CMD ["bin/inventory.management.system"]
