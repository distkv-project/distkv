# dst [![Build Status](https://travis-ci.com/dst-project/dst.svg?branch=master)](https://travis-ci.com/dst-project/dst) 
A distributed key-value in-memory store system with table concept.

Full [document](https://docs.dst-pro.com) is here.

## Project Description
`Dst` project is a memory-based distributed key-value storage system. Besides these features, dst can support table concept which looks like tables in relational databases.
We use java to finish this project, which is somewhat different from most databases using c/c++.

## Fancy Features
1. Distributed
2. Table Concept
3. Sorted List

## Quick Started
When we connect to server, we must start server first. At present, we only have two kinds of clients. One is `command line tool`, another is `java client sdk`.
We can use each of them to access dst server.
#### 1. Start Dst Server
```bash
[root@localhost ~]# run_dst_server.sh
```

#### 2.1 Start Dst Cli
```bash
[root@localhost ~]# run_dst_cli.sh
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
```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.dst.client.DefaultDstClient;

public class DstUsageExample {
  public static void main(String[] args) {
    DefaultDstClient dstClient = new DefaultDstClient("list://127.0.0.1:8082");
    if (dstClient.isConnected()) {
      dstClient.strs().put("k1", "v1");
      dstClient.sets().put("k1", new HashSet<>(Arrays.asList("v1", "v2", "v3", "v3")));
      dstClient.lists().put("k1", new ArrayList<>(Arrays.asList("v1", "v2", "v3")));
      Map<String, String> map = new HashMap<>();
      map.put("k1", "v1");
      map.put("k2", "v2");
      map.put("k3", "v3");
      map.put("k4", "v4");
      dstClient.dicts().put("dict1", map);

      String strResult = dstClient.strs().get("k1");
      Set<String> setResult = dstClient.sets().get("k1");
      List<String> listResult = dstClient.lists().get("k1");
      Map<String, String> mapResult = dstClient.dicts().get("dict1");

      //print String result
      System.out.println("The result of dstClient.strs().get(\"k1\") is: " + strResult);

      //print set result
      System.out.println("The result of dstClient.sets().get(\"k1\") is: " + setResult);

      //print list result
      System.out.println("The result of dstClient.lists().get(\"k1\") is: " + listResult);

      //print dictionary result
      System.out.println("The result of dstClient.dicts().get(\"dict1\") is: " + mapResult);

    }
  }
}
```

## Getting Involved
Thank you for your attention to the `Dst` project. If you have any questions, you can create a new issue in our [Issues](https://github.com/dst-project/dst/issues) list.
And we welcome you to participate in our `Dst` project, if you want to make some contributions, you can refer the file [CONTRIBUTING.md](https://github.com/dst-project/dst/blob/master/CONTRIBUTING.md).

