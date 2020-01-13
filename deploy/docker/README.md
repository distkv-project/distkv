

# To start dst-docker 
If you want to build dst-docker right away there four options:

1. ```docker``` and  ```docker-compose``` environment.

2. Put the packaged ```dst.jar``` into the current directory.

3. Modify the variables in ```docker-compose.yaml```.

    *  You can set docker container size by　```mem_limit```.

    *  You can set JVM options by　```JAVA_OPTS```.

4. Execute shell in current directory:

    * start up: ```docker-compose up -d```

    * query: ```docker-compose　ps``` or ```docker stats``` or ```docker logs```

    * stop: ```docker-compose stop```

    * enter: ```docker exec -it dst bash```






