# Overview
Homepage: [https://distkv.com](https://distkv.com)  
Demo site: [https://distkv.cloud](https://distkv.cloud)  
Github: [https://github.com/distkv-project/distkv](https://github.com/distkv-project/distkv)  

## Project Description
`Distkv` project is a distributed key-value in-memory database system. Besides these features, `Distkv` supports table concept which looks like tables in relational databases. It also supports a rich ecosystem, named `Pine`, which has many easy-to-use and out-of-the-box components for Web application and micro-service.

## Awesome Features
1. Redis-like data structure
2. Table concept based on kv store
3. Strong consistency model
4. Easy to use clients
5. Strong ecosystem for Web application and micro-service

## Quick Started
#### 1. Install Distkv
First make sure you have the python environment.

Then running the following command to install the Distkv locally:
```bash
pip install -e deploy/python -v
```

#### 2. Start Distkv server
Running the following command to start the Distkv server.
```bash
$ dkv-server
```

#### 3. Start Distkv command line tool
Running the following command to start the command line tool of a Distkv client.
```bash
$ dkv-cli
```

#### 4. Command Line Tool
Once you started the command line tool `dkv-cli` successfully, type the following command to enjoy the trip of Distkv.
```bash
$ dkv-cli > str.put k1 v2
$ dkv-cli > ok
$ dkv-cli > str.get k1
$ dkv-cli > v1
```

## Client SDK
Now we support both `Java Client SDK` and `Java Async Client SDK`. We are working in progress to support other languages SDK. 

**Configure you dependency of Distkv client in `pom.xml`:**
```xml
<dependency>
    <groupId>com.distkv</groupId>
    <artifactId>distkv-client</artifactId>
    <version>0.1.3</version>
</dependency>
```

**To use Distkv client in Java, see the following examples:**

- [Java Client SDK Example](https://github.com/distkv-project/distkv/blob/master/client/src/main/java/com/distkv/client/example/DistkvUsageExample.java)

- [Java Async Client SDK Example](https://github.com/distkv-project/distkv/blob/master/client/src/main/java/com/distkv/asyncclient/example/DistkvAsyncUsageExample.java)
