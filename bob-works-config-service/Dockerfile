FROM amazoncorretto:17.0.5
WORKDIR app
ARG JAR_FILE=build/libs/bob-works-config-service.jar
COPY ${JAR_FILE} application.jar

ENV TZ=Asia/Seoul

HEALTHCHECK --interval=10s --timeout=3s --retries=5 CMD curl --fail http://localhost:8888/actuator/health || exit 1
CMD ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "application.jar"]
