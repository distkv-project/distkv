<div align=left>
    <img src="https://distkv.com/img/distkv-logo.png" width="282" height="170"> 
</div>

[![distkv build](https://github.com/distkv-project/distkv/workflows/distkv_build/badge.svg)](https://github.com/distkv-project/distkv/actions)
[![codebeat badge](https://codebeat.co/badges/4d3ca0ed-06a6-4f43-b866-2b663e65e0f4)](https://codebeat.co/projects/github-com-distkv-project-distkv-master) 
[![codecov](https://codecov.io/gh/distkv-project/distkv/branch/master/graph/badge.svg)](https://codecov.io/gh/distkv-project/distkv)

A distributed key-value database system with table concept.

# Project Description
`Distkv` project is a distributed key-value database system. Besides these features, `Distkv` supports table concept which looks like tables in relational databases. It also supports a rich ecosystem, named `Pine`, which has many easy-to-use and out-of-the-box components for Web application and micro-service.

Full document is https://distkv.com

# Awesome Features
1. Redis-like data structure
2. Table concept based on kv store
3. Strong consistency model
4. Easy to use clients
5. Strong ecosystem for Web application and micro-service

# Quick Started
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
dkv-cli > list.put "k1" "v1" "v2" "v3"
dkv-cli > ok
dkv-cli > list.get "k1"
dkv-cli > ["v1", "v2", "v3"]

dkv-cli > int.put "k1" 1
dkv-cli > ok
dkv-cli > int.incr "k1" 2
dkv-cli > ok
dkv-cli > int.get "k1"
dkv-cli > 3
```

# Client SDK
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

# Who Is Using
<div align=left>
    <img src="https://distkv.com/img/who_is_using_logos/yige_logo.png" width="200" height="200"> 
</div>

# Getting Involved
Thank you for your attention to the `Distkv` project. If you have any questions, you can create a new issue in our [Issues](https://github.com/distkv-project/distkv/issues) list.
We also welcome you to participate in our `Distkv` project, if you want to make contributions, you can refer the file [CONTRIBUTING.md](https://github.com/distkv-project/distkv/blob/master/CONTRIBUTING.md).
