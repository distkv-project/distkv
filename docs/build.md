
The environment requirements of Dst are:

- JDK >= 1.8.0
- maven >= 3.5.0
- protobuf == 2.5.0 (Not support 3.0 yet)

Then use the following command to build Dst:
```shell
mvn clean install -DskipTests
```

If there is no error appearing, congratulations, you have succeeded to build Dst.

If you'd like to run the tests, use this command:
```shell
mvn test
```

next: [Run Dst server](https://dst-project.github.io/dst/run_dst_server)
