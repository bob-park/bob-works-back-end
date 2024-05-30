FROM amazoncorretto:17.0.7
WORKDIR app
ARG JAR_FILE=build/libs/bob-works-authorization-service.jar
COPY ${JAR_FILE} application.jar

ENV TZ=Asia/Seoul

HEALTHCHECK --interval=10s --timeout=3s --retries=5 CMD curl --fail http://localhost:9000/actuator/health || exit 1
CMD ["java", "-Xmx128m", "-jar", "application.jar"]