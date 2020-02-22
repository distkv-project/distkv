

# Start distkv-docker 

If you want to build distkv-docker, follow these 3 steps:

1. Make sure you have `docker` and  `docker-compose` environment.

2. Reset the variables in `docker-compose.yaml`.

    *  You can set the size of docker container with `mem_limit`.

    *  You can set JVM options with　`JAVA_OPTS`.

3. Execute shell in the working directory:

    * start up: ```docker-compose up -d```

    * query: ```docker-compose　ps``` or ```docker stats``` or ```docker logs```

    * stop: ```docker-compose stop```

    * enter: ```docker exec -it distkv bash```
