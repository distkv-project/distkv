FROM openjdk:8-jdk-slim
WORKDIR /

ENV JAVA_OPTS='-Xms820m -Xmx820m'

ADD distkv_start.sh /distkv_start.sh

RUN java -version && \
    chmod +x /distkv_start.sh && \
    env

ADD distkv.jar /distkv.jar

EXPOSE 8082

ENTRYPOINT ["/distkv_start.sh"]
