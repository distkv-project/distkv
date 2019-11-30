## Usage

In this section, you can learn some basic concepts in Dst, and learn how to use these concepts as well.

#### 1. String concept

Put a string value into the Dst store.  
```bash
str.put key value
```
example:
```bash
dst-cli > str.put k1 v1
dst-cli > ok
```
Get a string value from Dst store.
```bash
str.get key
```
example:
```bash
dst-cli > str.get k1
dst-cli > v1
```

Drop a string from the Dst store.
```bash
str.drop key
```
example:
```bash
dst-cli > str.drop k1
dst-cli > ok

dst-cli > str.get k1
dst-cli > Error(A202): The Key `k1` is not found in the store.
```

#### 2. List concept

Put a list into Dst store.
```bash
list.put key value1 [value2 [value3 [...]]]
```
example:
```bash
dst-cli > list.put k1 v1 v2 v3
dst-cli > ok
```

Get a list from Dst store.
```bash
list.get key
```

example:
```bash
dst-cli > list.get k1
dst-cli > [v1, v2, v3]
```

Get a range of items of the list from Dst store.
```bash
# Note that it's excluding the `end_index`.
list.get key from_index end_index
```

example:
```bash
dst-cli > list.get k1 0 2
dst-cli > [v1, v2]
```

Get one item of the list from Dst store.
```bash
list.get key index
```
example:
```bash
dst-cli > list.get k1 1
dst-cli > v1
```

dst-cli > list.lput "k1" "v4" "v5" "v6"
dst-cli > ok

dst-cli > list.rput "k1" "v7"
dst-cli > ok

dst-cli > list.ldel "k1" 2
dst-cli > ok

dst-cli > list.rdel "k1" 2
dst-cli > ok

dst-cli > list.get "k1"
dst-cli > ["v6", "v1", "v2"]
```

#### 3. Set concept
```bash
dst-cli > set.put "k1" "v1" "v2" "v3"
dst-cli > ok

dst-cli > set.get "k1"
dst-cli > {"v1", "v2", "v3"}

dst-cli > set.del "k1" "v1"
dst-cli > ok

dst-cli > set.get "k1"
dst-cli > {"v2", "v3"}

dst-cli > set.exists "k1" "v2"
dst-cli > true

dst-cli > set.exists "k1" "v4"
dst-cli > false

dst-cli > set.drop "k1"
dst-cli > ok
```

#### 4. Dict concept
```bash
dst-cli > dict.put "dict1" "k1" "v1" "k2" "v2"
dst-cli > ok

dst-cli > dict.get "dict1"
dst-cli > { "k1" : "v1", "k2" : "v2"}

dst-cli > dict.get "dict1" "k1"
dst-cli > "v1"

dst-cli > dict.get "dict1" "k1"
dst-cli > "v1"

dst-cli > dict.del "dict1" "k1"
dst-cli > ok

dst-cli > dict.pop "dict1" "k2"
dst-cli > "v2"

dst-cli > dict.get "dict1"
dst-cli > []

dst-cli > dict.drop "dict1"
dst-cli > ok
```

#### 5. Table concept
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
2. Start an dst server and execute this command to create table:
```shell
> dst-cli -p 12344 # connect to dst server
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
