<div align=left>
    <img src="docs/res/dst_logo.png" width="282" height="170"> 
</div>

[![Build Status](https://travis-ci.com/distkv-project/distkv.svg?branch=master)](https://travis-ci.com/distkv-project/distkv) 
[![codebeat badge](https://codebeat.co/badges/4d3ca0ed-06a6-4f43-b866-2b663e65e0f4)](https://codebeat.co/projects/github-com-distkv-project-distkv-master) 
[![codecov](https://codecov.io/gh/distkv-project/distkv/branch/master/graph/badge.svg)](https://codecov.io/gh/distkv-project/distkv)

A distributed key-value in-memory store system with table concept.

## Project Description
`DistKV` project is a memory-based distributed key-value storage system. Besides these features, `DistKV` also support table concept which looks like tables in relational databases. We use `Java` to finish this project, which is somewhat different from most databases using `C/C++`.

Full document is https://distkv.com

## Awesome Features
1. Redis-like data structure
2. Table concept based on KV Store
3. High available because it's distributed
4. Easy to use client
5. Strong ecosystem for Web application

## Quick Started
#### 1. Install DistKV
Running the scripts `scripts/install_distkv.sh` will install the whole `DistKV` to your machine.
```bash
./scripts/install_distkv.sh
```

When we connect to server, we must start server firstly. At present, we only have two kinds of clients. One is `Command Line Tool`, another is `Java Client SDK`.
We can use each of them to access distkv server.
#### 2. Start DistKV Server
```bash
[root@localhost ~]# distkv-server
```

#### 3. Start DistKV Cli
```bash
[root@localhost ~]# distkv-cli
```

#### 4. Command Line Tool
```bash
distkv-cli > str.put "k1" "v1"
distkv-cli > ok
distkv-cli > str.get "k1" 
distkv-cli > "v1"

distkv-cli > list.put "k1" "v1" "v2" "v3"
distkv-cli > ok
distkv-cli > list.get "k1"
distkv-cli > ["v1", "v2", "v3"]

distkv-cli > set.put "k1" "v1" "v2" "v3"
distkv-cli > ok
distkv-cli > set.get "k1"
distkv-cli > {"v1", "v2", "v3"}

distkv-cli > dict.put "dict1" "k1" "v1" "k2" "v2"
distkv-cli > ok
distkv-cli > dict.get "dict1"
distkv-cli > { "k1" : "v1", "k2" : "v2"}

distkv-cli > slist.put "k1" "m1" 4 "m2" -4 "m3" 0
distkv-cli > ok
distkv-cli > slist.top "k1" 2
distkv-cli > [("m1", 4), ("m3", 0)]
distkv-cli > slist.getMember "k1" "m2"
distkv-cli > ("m2", -4), 3rd
```

## Java Client SDK
[Java Client SDK Example](https://github.com/distkv-project/distkv/blob/master/client/src/main/java/com/distkv-project/distkv/client/example/DstUsageExample.java)

## Java Async Client SDK
[Java Async Client SDK Example](https://github.com/distkv-project/distkv/blob/master/client/src/main/java/com/distkv-project/distkv/asyncclient/example/DstAsyncUsageExample.java)

## Getting Involved
Thank you for your attention to the `DistKV` project. If you have any questions, you can create a new issue in our [Issues](https://github.com/distkv-project/distkv/issues) list.
And we welcome you to participate in our `DistKV` project, if you want to make some contributions, you can refer the file [CONTRIBUTING.md](https://github.com/distkv-project/distkv/blob/master/CONTRIBUTING.md).
