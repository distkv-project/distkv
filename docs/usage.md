## Usage

### Client tool

#### 1. String concept
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

#### 2. List concept
```bash
dst-cli > list.put "k1" "v1" "v2" "v3"
dst-cli > ok

dst-cli > list.get "k1"
dst-cli > ["v1", "v2", "v3"]

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
