
# dst [![Build Status](https://travis-ci.com/dst-project/dst.svg?branch=master)](https://travis-ci.com/dst-project/dst) [![codebeat badge](https://codebeat.co/badges/4d3ca0ed-06a6-4f43-b866-2b663e65e0f4)](https://codebeat.co/projects/github-com-dst-project-dst-master) [![codecov](https://codecov.io/gh/dst-project/dst/branch/master/graph/badge.svg)](https://codecov.io/gh/dst-project/dst)
A distributed key-value in-memory store system with table concept.

Full [document](https://docs.dst-pro.com) is here.

## Project Description
`Dst` project is a memory-based distributed key-value storage system. Besides these features, dst can support table concept which looks like tables in relational databases. We use java to finish this project, which is somewhat different from most databases using c/c++.

## Awesome Features
1. Redis-like data structure
2. Table concept based on k-v store
3. High available since this is distributed
4. Easy to use client
5. Strong ecosystem for web applications

## Quick Started
#### 1. install dst
Running the scripts `install_dst.sh` will install the whole dst to your machine.
```bash
./install_dst.sh
```

When we connect to server, we must start server first. At present, we only have two kinds of clients. One is `command line tool`, another is `java client sdk`.
We can use each of them to access dst server.
#### 1. Start Dst Server
```bash
[root@localhost ~]# dst-server
```

#### 2.1 Start Dst Cli
```bash
[root@localhost ~]# dst-cli
```

#### 2.2 Command Line Tool
```bash
dst-cli > str.put "k1" "v1"
dst-cli > ok
dst-cli > str.get "k1" 
dst-cli > "v1"
dst-cli > list.put "k1" "v1" "v2" "v3"
dst-cli > ok
dst-cli > list.get "k1"
dst-cli > ["v1", "v2", "v3"]
dst-cli > set.put "k1" "v1" "v2" "v3"
dst-cli > ok
dst-cli > set.get "k1"
dst-cli > {"v1", "v2", "v3"}
dst-cli > dict.put "dict1" "k1" "v1" "k2" "v2"
dst-cli > ok
dst-cli > dict.get "dict1"
dst-cli > { "k1" : "v1", "k2" : "v2"}
dst-cli > str.put "k1" "v1"   # the same as `put`
dst-cli > ok 
```

#### 3. Java Client SDK
[Java Client SDK Example](https://github.com/dst-project/dst/blob/master/client/src/main/java/org/dst/client/example/DstUsageExample.java)

#### 4. Java Async Client SDK
[Java Async Client SDK Example](https://github.com/dst-project/dst/blob/master/client/src/main/java/org/dst/asyncclient/example/DstAsyncUsageExample.java)

## Getting Involved
Thank you for your attention to the `Dst` project. If you have any questions, you can create a new issue in our [Issues](https://github.com/dst-project/dst/issues) list.
And we welcome you to participate in our `Dst` project, if you want to make some contributions, you can refer the file [CONTRIBUTING.md](https://github.com/dst-project/dst/blob/master/CONTRIBUTING.md).

