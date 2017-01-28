# which image my image is based on
FROM java:8

LABEL maintainer "Joshua.Olson@ILMservice.com"

# necessary for Spring Boot apps that write in the filesystem
VOLUME /tmp

# https://docs.docker.com/engine/reference/builder/#/add
ADD ILMServices-FantasyFootball-0.0.1-SNAPSHOT.jar app.jar

# static content (e.g. "index.html") requires the file to have a modification time
RUN sh -c 'touch /app.jar'

EXPOSE 8080

ENV JAVA_OPTS=""

# format: ENTRYPOINT ["executable", "param1", "param2"] (exec form, preferred)
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
# -c means "execute the command instead of bash"
# -D reduces Tomcat startup time
