FROM amazoncorretto:17.0.5
WORKDIR application
ARG JAR_FILE=build/libs/bob-works-user-service.jar
COPY ${JAR_FILE} application.jar
HEALTHCHECK --interval=30s --timeout=3s --retries=5 CMD curl --fail http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["java", "-Xmx512m", "-jar", "application.jar"]
