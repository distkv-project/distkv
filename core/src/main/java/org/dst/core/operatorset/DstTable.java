package org.dst.core.operatorset;

import org.dst.core.table.Field;
import org.dst.core.table.Record;
import org.dst.core.table.TableSpecification;
import org.dst.core.table.Value;

import java.util.List;
import java.util.Map;

public interface DstTable {

  /**
   * This method will create a new table
   *
   * @param table the key to store
   */
  void createTable(TableSpecification table);

  /**
   * This method will append list records into table
   *
   * @param tableName tableName
   * @param records   records
   */
  void append(String tableName, List<Record> records);

  /**
   * This method will return table info by table name
   *
   * @param tableName table name
   */
  TableSpecification findTableSpecification(String tableName);

  /**
   * This method will return all values by TableSpecification
   *
   * @param tableName  tableName
   * @param conditions support for conditional query
   */
  List<Record> query(String tableName, Map<Field, Value> conditions);

  /**
   * This method will drop table from store by tableName
   *
   * @param tableName tableName
   * @return whether drop
   */
  boolean drop(String tableName);


  /**
   * This method will clear the table by table description
   *
   * @param tableName tableName
   */
  void clearTable(String tableName);

  /**
   * This method will clear the whole table store
   */
  void clear();

}
