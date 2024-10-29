FROM amazoncorretto:17-alpine3.18
WORKDIR app
ARG JAR_FILE=build/libs/bob-works-document-service.jar
COPY ${JAR_FILE} app.jar

ENV TZ=Asia/Seoul

CMD ["java", "-jar", "app.jar"]