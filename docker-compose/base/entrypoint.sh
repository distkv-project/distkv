#!/bin/bash

source /etc/profile

case $MEMORY_SIZE in
    "128")
       export JAVA_OPTS="-Xms90m -Xmx90m "
       echo "Optimizing java process for 128M Memory...." >&2
       ;;
    "256")
       export JAVA_OPTS="-Xms180m -Xmx180m "
       echo "Optimizing java process for 256M Memory...." >&2
       ;;
    "512")
       export JAVA_OPTS="-Xms360m -Xmx360m "
       echo "Optimizing java process for 512M Memory...." >&2
       ;;
    "1024")
       export JAVA_OPTS="-Xms720m -Xmx720m "
       echo "Optimizing java process for 1G Memory...." >&2
       ;;
    "2048")
       export JAVA_OPTS="-Xms1440m -Xmx1440m "
       echo "Optimizing java process for 2G Memory...." >&2
       ;;
    "4096")
       export JAVA_OPTS="-Xms2880m -Xmx2880m "
       echo "Optimizing java process for 4G Memory...." >&2
       ;;
    "8192")
       export JAVA_OPTS="-Xms6144m -Xmx6144m "
       echo "Optimizing java process for 8G Memory...." >&2
       ;;
     "8GMORE")
       export JAVA_OPTS="-Xms8G -Xmx8G "
       echo "Optimizing java process for biger Memory...." >&2
       ;;
    *)
       export JAVA_OPTS="-Xms2880m -Xmx2880m "
       echo "MEMORY_SIZE environment variable is not set, use the default memory settings" >&2
       ;;
esac

java -jar $JAVA_OPTS /opt/dst-server.jar
