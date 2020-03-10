FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
ENV JAVA_GC="-XX:+PrintGCDetails \
-XX:+PrintGCDateStamps \
-Xloggc:/log/gc/spring_boot_demo_gc_%p.log \
-XX:+UseGCLogFileRotation \
-XX:NumberOfGCLogFiles=5"
ADD target/rdep-gateway-1.0-SNAPSHOT.jar gateway.jar
#RUN mkdir -p /log/gc
EXPOSE 9999
#ENTRYPOINT exec java $JAVA_OPTS $JAVA_GC -jar spring-boot-demo.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar gateway.jar --zk.host=host.docker.internal --redis.host=host.docker.internal