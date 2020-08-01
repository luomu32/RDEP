FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
ENV JAVA_GC="-XX:+PrintGCDetails \
-XX:+PrintGCDateStamps \
-Xloggc:/log/gc/spring_boot_demo_gc_%p.log \
-XX:+UseGCLogFileRotation \
-XX:NumberOfGCLogFiles=5"
ENV TZ=Asia/Shanghai
ADD target/config-server-1.0-SNAPSHOT.jar /home/config-server.jar
#设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

#EXPOSE 8600
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /home/config-server.jar
