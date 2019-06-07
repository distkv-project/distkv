# dst
A distributed key-value in-memory store system with table concept.

### Usage
1. Define your data structure in a schema file named `mytables.sc`
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
