FROM amazoncorretto:17.0.7
WORKDIR application
#ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
ARG JAR_FILE=build/libs/bob-works-maintenance-service.jar
COPY ${JAR_FILE} application.jar
HEALTHCHECK --interval=10s --timeout=3s --retries=5 CMD curl --fail http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "application.jar"]