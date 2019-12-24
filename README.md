
# dst [![Build Status](https://travis-ci.com/dst-project/dst.svg?branch=master)](https://travis-ci.com/dst-project/dst) [![codebeat badge](https://codebeat.co/badges/4d3ca0ed-06a6-4f43-b866-2b663e65e0f4)](https://codebeat.co/projects/github-com-dst-project-dst-master) [![codecov](https://codecov.io/gh/dst-project/dst/branch/master/graph/badge.svg)](https://codecov.io/gh/dst-project/dst)
A distributed key-value in-memory store system with table concept.

Full [document](https://docs.dst.distkv.com/en/latest/) is here.

![dst](docs/res/logo.png)

## Project Description
`Dst` project is a memory-based distributed key-value storage system. Besides these features, dst can support table concept which looks like tables in relational databases. We use `Java` to finish this project, which is somewhat different from most databases using `C/C++`.

## Awesome Features
1. Redis-like data structure
2. Table concept based on KV Store
3. High available because it's distributed
4. Easy to use client
5. Strong ecosystem for Web application

## Quick Started
#### 1. Install Dst
Running the scripts `scripts/install_dst.sh` will install the whole `Dst` to your machine.
```bash
./scripts/install_dst.sh
```

When we connect to server, we must start server firstly. At present, we only have two kinds of clients. One is `Command Line Tool`, another is `Java Client SDK`.
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

dst-cli > slist.put "k1" "m1" 4 "m2" -4 "m3" 0
dst-cli > ok
dst-cli > slist.top "k1" 2
dst-cli > [("m1", 4), ("m3", 0)]
dst-cli > slist.getMember "k1" "m2"
dst-cli > ("m2", -4), 3rd
```

#### 3. Java Client SDK
[Java Client SDK Example](https://github.com/dst-project/dst/blob/master/client/src/main/java/com/distkv/dst/client/example/DstUsageExample.java)

#### 4. Java Async Client SDK
[Java Async Client SDK Example](https://github.com/dst-project/dst/blob/master/client/src/main/java/com/distkv/dst/asyncclient/example/DstAsyncUsageExample.java)

## Getting Involved
Thank you for your attention to the `Dst` project. If you have any questions, you can create a new issue in our [Issues](https://github.com/dst-project/dst/issues) list.
And we welcome you to participate in our `Dst` project, if you want to make some contributions, you can refer the file [CONTRIBUTING.md](https://github.com/dst-project/dst/blob/master/CONTRIBUTING.md).

