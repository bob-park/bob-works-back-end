FROM amazoncorretto:17.0.7
WORKDIR app
#ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
ARG JAR_FILE=build/libs/bob-works-discovery-service.jar

COPY ${JAR_FILE} application.jar

ENV TZ=Asia/Seoul

HEALTHCHECK --interval=10s --timeout=3s --retries=5 CMD curl --fail http://localhost:8761/actuator/health || exit 1
CMD ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "application.jar"]