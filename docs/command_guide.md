## Introduction
Distkv is a type-oriented database system. There are several concepts in Distkv that looks like the basic collections.
We have 6 concepts `string`, `list`, `set`, `dict`, `sortedlist` and `table` yet.

In this section, you will learn the grammer of these basic concepts in Distkv, and will learn how to use these concepts as examples as well.

## 1. String Concept


Put a string value into Distkv store.
```bash
str.put key value
```
example:
```bash
$ dkv-cli > str.put k1 v1
$ dkv-cli > ok
```

Get a string value from Distkv store.
```bash
str.get key
```
example:
```bash
dkv-cli > str.get k1
dkv-cli > v1
```

Drop a string value from Distkv store.
```bash
str.drop key
```
example:
```bash
dkv-cli > str.drop k1
dkv-cli > ok

dkv-cli > str.get k1
dkv-cli > errorCode: A100;
 Detail: The key k1 is not found in the store.
```

## 2. List Concept

Put a list into Distkv store.
```bash
list.put key value1 [value2 [value3 [...]]]
```
example:
```bash
dkv-cli > list.put k1 v1 v2 v3
dkv-cli > ok
```

Get a list from Distkv store.
```bash
list.get key
```
example:
```bash
dkv-cli > list.get k1
dkv-cli > [v1, v2, v3]
```

Get a range of items of the list from Distkv store.
```bash
# Note that it's excluding the `end_index`.
list.get key from_index end_index
```
example:
```bash
dkv-cli > list.get k1 0 2
dkv-cli > [v1, v2]
```

Get one item of the list from Distkv store.
```bash
list.get key index
```
example:
```bash
dkv-cli > list.get k1 1
dkv-cli > v1
```

Put items into the front of the list.
```bash
list.lput key value1 [value2 [value3 [...]]]
```
example:
```bash
dkv-cli > list.lput "k1" "v4" "v5" "v6"
dkv-cli > ok
```

Put items into the back of the list.
```bash
list.rput key value1 [value2 [value3 [...]]]
```
example:
```bash
dkv-cli > list.rput "k1" "v7"
dkv-cli > ok
```

Remove item from the list with the given index.
```bash
list.ldel key index
```
example:
```bash
dkv-cli > list.ldel "k1" 2
dkv-cli > ok
```

Remove item from the back of the list with the given back index.
```bash
list.rdel key back_index
```
If the list is [1, 2, 3] and the given number is 0, item `3` will be removed.

example:
```bash
dkv-cli > list.rdel "k1" 2
dkv-cli > ok
```

Get the list from Distkv store.
```bash
list.get key
```
example:
```bash
dkv-cli > list.get "k1"
dkv-cli > ["v6", "v1", "v2"]
```

## 3. Set concept
```bash
dkv-cli > set.put "k1" "v1" "v2" "v3"
dkv-cli > ok

dkv-cli > set.get "k1"
dkv-cli > {"v1", "v2", "v3"}

dkv-cli > set.del "k1" "v1"
dkv-cli > ok

dkv-cli > set.get "k1"
dkv-cli > {"v2", "v3"}

dkv-cli > set.exists "k1" "v2"
dkv-cli > true

dkv-cli > set.exists "k1" "v4"
dkv-cli > false

dkv-cli > set.drop "k1"
dkv-cli > ok
```

## 4. Dict concept
```bash
dkv-cli > dict.put "dict1" "k1" "v1" "k2" "v2"
dkv-cli > ok

dkv-cli > dict.get "dict1"
dkv-cli > { "k1" : "v1", "k2" : "v2"}

dkv-cli > dict.get "dict1" "k1"
dkv-cli > "v1"

dkv-cli > dict.get "dict1" "k1"
dkv-cli > "v1"

dkv-cli > dict.del "dict1" "k1"
dkv-cli > ok

dkv-cli > dict.pop "dict1" "k2"
dkv-cli > "v2"

dkv-cli > dict.get "dict1"
dkv-cli > []

dkv-cli > dict.drop "dict1"
dkv-cli > ok
```
## 5. SortedList Concept

## 6. Table concept
1. Define your data structure in a schema file named `mytables.sc`
```shell
table TaskTable {
  [p]task_id: string;
  [i]driver_id: string;
  task_name: string;
  return_num: int;
  arguments: [string];
}
```
```shell
table DriverTable {
 [p]driver_id: string;
 driver_name: string;
 actor_num: int;
};
```
2. Start an distkv server and execute this command to create table:
```shell
> dkv-cli -p 12344 # connect to distkv server
> create TaskTable, DriverTable from mytables.sc
```
3. Add data to the table:
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
4. Query all tasks by driver id:
```shell
> TaskTable.query (*) when driver_id == "22222"
<
< task_id      driver_id     task_name      num_return      arguments
< "00001"      "22222"       "my_task"      3               ["1", "2"]
< "00003"      "22222"       "my_task"      3               ["1", "2"]
< 2 records
```
