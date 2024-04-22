FROM maven:3.8.3-openjdk-17 AS maven_build
WORKDIR /tmp/
COPY pom.xml /tmp/
RUN mvn dependency:go-offline
COPY src /tmp/src/
RUN mvn clean
RUN mvn package -Dmaven.test.skip=true


#pull base image
FROM openjdk:17-alpine
EXPOSE 80
ENV JAVA_OPTS="-Xms2048m -Xmx3072m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=512m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dlog4j2.formatMsgNoLookups=true -jar /app.jar" ]
COPY --from=maven_build /tmp/target/*.jar /app.jar
