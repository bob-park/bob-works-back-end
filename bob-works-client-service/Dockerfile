FROM amazoncorretto:17-alpine3.18
WORKDIR app
ARG JAR_FILE=build/libs/bob-works-client-service.jar
COPY ${JAR_FILE} application.jar

ENV TZ=Asia/Seoul

CMD ["java", "-Xmx128m", "-jar", "application.jar"]