FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
ENV JAVA_GC="-XX:+PrintGCDetails \
-XX:+PrintGCDateStamps \
-Xloggc:/log/gc/spring_boot_demo_gc_%p.log \
-XX:+UseGCLogFileRotation \
-XX:NumberOfGCLogFiles=5"
ADD target/rdep-middleware-1.0-SNAPSHOT.jar middleware.jar
EXPOSE 9999
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar middleware.jar --mysql.host=host.docker.internal --zk.host=host.docker.internal