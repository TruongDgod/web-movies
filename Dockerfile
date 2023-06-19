FROM openjdk:8-jdk-alpine
ENV TZ=Asia/Ho_Chi_Minh
VOLUME /tmp
EXPOSE 8787
ARG JAR_FILE=build/libs/anti-money-laundering-system-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} /app.jar
RUN apk --update add fontconfig ttf-dejavu
ENTRYPOINT ["java","-jar","/app.jar"]