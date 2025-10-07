# See also docker-compose.yaml
# docker build -t random-service .

# stage 1: extract JAR layers
FROM eclipse-temurin:25-jdk-alpine AS build
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/app.jar
WORKDIR /app
RUN mkdir -p dependency && (cd dependency; jar -xf /app/app.jar)

# stage 2: runtime image
FROM eclipse-temurin:25-jre-alpine
VOLUME /tmp

ARG DEPENDENCY=/app/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8080
ENTRYPOINT ["java","-cp","app:app/lib/*","com.example.random.RandomServiceApplication"]
