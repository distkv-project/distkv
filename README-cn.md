# dst [![Build Status](https://travis-ci.com/jovany-wang/dst.svg?token=7wQpC1f51BkWzsCeefLE&branch=master)](https://travis-ci.com/jovany-wang/dst)
一个可以存储table的分布式"key-value"内存存储系统。

## 快速开始
#### 1. 环境要求
JDK >= 1.8 

maven >= 3.5.0

protobuf == 2.5.0 (Not support 3.0)
#### 2. 执行命令
```
mvn clean install -Dskiptests
```
#### 3. 测试命令
```
mvn test
```
## 使用演示

#### 1. 字符串
```bash
dst-cli > put "k1" "v1"
dst-cli > ok

dst-cli > str.put "k1" "v1"   # the same as `put`
dst-cli > ok

dst-cli > get "k1"
dst-cli > "v1"

dst-cli > str.get "k1"       # the same as `get`
dst-cli > "v1"
```

#### 2. List
```bash
dst-cli > list.put "k1" "v1" "v2" "v3"
dst-cli > ok

dst-cli > list.get "k1"
dst-cli > ["v1", "v2", "v3"]

dst-cli > list.lpush "k1" "v4" "v5" "v6"
dst-cli > ok

dst-cli > list.rpush "k1" "v7"
dst-cli > ok

dst-cli > list.lpop "k1" 2
dst-cli > ok

dst-cli > list.get "k1"
dst-cli > ["v6", "v1", "v2", "v3", "v7"]
```

#### 3. Set
```bash
dst-cli > set.put "k1" "v1" "v2" "v3"
dst-cli > ok

dst-cli > set.get "k1"
dst-cli > {"v1", "v2", "v3"}

dst-cli > set.exists "k1" "v2"
dst-cli > true

dst-cli > set.exists "k1" "v4"
dst-cli > false
```

#### 4. 字典 
```bash
dst-cli > dict.put "dict1" "k1" "v1" "k2" "v2"
dst-cli > ok

dst-cli > dict.get "dict1"
dst-cli > { "k1" : "v1", "k2" : "v2"}

dst-cli > dict.get "dict1" "k1"
dst-cli > "v1"
```

#### 5. Table concept
1. 在约束文件`mytables.sc` 里面定义数据结构
```
table TaskTable {
  [p]task_id: string;
  [i]driver_id: string;
  task_name: string;
  return_num: int;
  arguments: [string];
}

table DriverTable {
 [p]driver_id: string;
 driver_name: string;
 actor_num: int;
};
```
2. 启动dst服务端并执行创建table的命令
```shell
> dst-cli -p 12344 # connect to dst server
> create TaskTable, DriverTable from mytables.sc
```
3. 往表里添加数据
```shell
> TaskTable.add "00001", "22222", "my_task", 3, ["1", "2"]
< ok
> TaskTable.add "00002", "99999", "my_task", 3, ["1", "2"]
< ok
> TaskTable.add "00003", "22222", "my_task", 3, ["1", "2"]
< ok
> DriverTable.add "22222", "my_driver", 10
< ok
```
4. 通过driver_id查询所有的结果
```shell
> TaskTable.query (*) when driver_id == "22222"
<
< task_id      driver_id     task_name      num_return      arguments
< "00001"      "22222"       "my_task"      3               ["1", "2"]
< "00003"      "22222"       "my_task"      3               ["1", "2"]
< 2 records
```
