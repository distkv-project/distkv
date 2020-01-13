FROM openjdk:8-jdk-slim
WORKDIR /

ENV JAVA_OPTS='-Xms820m -Xmx820m'

ADD dst_start.sh /dst_start.sh

RUN java -version && \
    chmod +x /dst_start.sh && \
    env

ADD dst.jar /dst.jar

EXPOSE 8082

ENTRYPOINT ["/dst_start.sh"]
