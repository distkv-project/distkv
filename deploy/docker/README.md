

# Start dst-docker 
If you want to build dst-docker, follow this 4 steps:

1. Make sure you have `docker` and  `docker-compose` environment.

2. Put the package `dst.jar` into the working directory.

3. Reset the variables in `docker-compose.yaml`.

    *  You can set the size of docker container with `mem_limit`.

    *  You can set JVM options with　`JAVA_OPTS`.

4. Execute shell in the working directory:

    * start up: ```docker-compose up -d```

    * query: ```docker-compose　ps``` or ```docker stats``` or ```docker logs```

    * stop: ```docker-compose stop```

    * enter: ```docker exec -it dst bash```
