package org.dst.core.operatorset;

import org.dst.core.table.Field;
import org.dst.core.table.Record;
import org.dst.core.table.TableSpecification;
import org.dst.core.table.Value;

import java.util.List;
import java.util.Map;

public interface DstTable {

  /**
   * Create a new table by the given table specification.
   *
   * @param table The specification of the table that will be created.
   */
  void createTable(TableSpecification table);

  /**
   * Append a list of records to a table.
   *
   * @param tableName tableName
   * @param records   The records that will be append to the table.
   */
  void append(String tableName, List<Record> records);

  /**
   * Find a table specification by tableName
   *
   * @param tableName tableName
   */
  TableSpecification findTableSpecification(String tableName);

  /**
   * Query target Records by tableName and query conditions
   *
   * @param tableName  tableName
   * @param conditions query conditions
   */
  List<Record> query(String tableName, Map<Field, Value> conditions);

  /**
   * Drop a table from store by tableName
   *
   * @param tableName tableName
   * @return drop result : true or false
   */
  boolean drop(String tableName);


  /**
   * Clear a table by tableName
   *
   * @param tableName tableName
   */
  void clearTable(String tableName);

  /**
   * Clear the whole table store
   */
  void clear();

}
