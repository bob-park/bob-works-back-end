FROM amazoncorretto:17.0.5
WORKDIR application
ARG JAR_FILE=build/libs/bob-works-config-service.jar
COPY ${JAR_FILE} application.jar
HEALTHCHECK --interval=10s --timeout=3s --retries=5 CMD curl --fail http://localhost:8888/actuator/health || exit 1
ENTRYPOINT ["java", "-Xmx512m", "-jar", "application.jar"]
