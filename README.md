<div align=left>
    <img src="docs/res/distkv_logo.png" width="282" height="170"> 
</div>

![distkv](https://github.com/distkv-project/distkv/workflows/distkv%20build/badge.svg)
[![codebeat badge](https://codebeat.co/badges/4d3ca0ed-06a6-4f43-b866-2b663e65e0f4)](https://codebeat.co/projects/github-com-distkv-project-distkv-master) 
[![codecov](https://codecov.io/gh/distkv-project/distkv/branch/master/graph/badge.svg)](https://codecov.io/gh/distkv-project/distkv)

A distributed key-value in-memory store system with table concept.

## Project Description
`DistKV` project is a memory-based distributed key-value storage system. Besides these features, `DistKV` also supports table concept which looks like tables in relational databases. We use `Java` to finish this project, which is somewhat different from most databases using `C/C++`.

Full document is https://distkv.com

## Awesome Features
1. Redis-like data structure
2. Table concept based on KV Store
3. Highly available
4. Easy to use clients
5. Strong ecosystem for Web application

## Quick Started
#### 1. Install DistKV
Running `scripts/install_distkv.sh` will install the whole `DistKV` to your machine.
```bash
./scripts/install_distkv.sh
```

When we connect to the server, we must first start the server. At present, we only have two kinds of clients. One is `Command Line Tool`, another is `Java Client SDK`.
We can use either of them to access the Distkv server.
#### 2. Start DistKV Server
```bash
[root@localhost ~]# dkv-server
```

#### 3. Start DistKV Cli
```bash
[root@localhost ~]# dkv-cli
```

#### 4. Command Line Tool
```bash
dkv-cli > str.put "k1" "v1"
dkv-cli > ok
dkv-cli > str.get "k1" 
dkv-cli > "v1"

dkv-cli > list.put "k1" "v1" "v2" "v3"
dkv-cli > ok
dkv-cli > list.get "k1"
dkv-cli > ["v1", "v2", "v3"]

dkv-cli > set.put "k1" "v1" "v2" "v3"
dkv-cli > ok
dkv-cli > set.get "k1"
dkv-cli > {"v1", "v2", "v3"}

dkv-cli > dict.put "dict1" "k1" "v1" "k2" "v2"
dkv-cli > ok
dkv-cli > dict.get "dict1"
dkv-cli > { "k1" : "v1", "k2" : "v2"}

dkv-cli > slist.put "k1" "m1" 4 "m2" -4 "m3" 0
dkv-cli > ok
dkv-cli > slist.top "k1" 2
dkv-cli > [("m1", 4), ("m3", 0)]
dkv-cli > slist.getMember "k1" "m2"
dkv-cli > ("m2", -4), 3rd
```

## Java Client SDK
[Java Client SDK Example](https://github.com/distkv-project/distkv/blob/master/client/src/main/java/com/distkv/client/example/DstUsageExample.java)

## Java Async Client SDK
[Java Async Client SDK Example](https://github.com/distkv-project/distkv/blob/master/client/src/main/java/com/distkv/asyncclient/example/DstAsyncUsageExample.java)

## Getting Involved
Thank you for your attention to the `DistKV` project. If you have any questions, you can create a new issue in our [Issues](https://github.com/distkv-project/distkv/issues) list.
We also welcome you to participate in our `DistKV` project, if you want to make contributions, you can refer the file [CONTRIBUTING.md](https://github.com/distkv-project/distkv/blob/master/CONTRIBUTING.md).
