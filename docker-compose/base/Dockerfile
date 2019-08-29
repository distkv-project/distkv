FROM centos
MAINTAINER hehuijun

RUN  yum install java-1.8.0-openjdk -y

ADD dst-server.jar /opt/dst-server.jar

ADD entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]

