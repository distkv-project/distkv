package com.distkv.dst.core.concepts;

import com.distkv.common.utils.Status;
import com.distkv.dst.core.table.Field;
import com.distkv.dst.core.table.Record;
import com.distkv.dst.core.table.TableSpecification;
import com.distkv.dst.core.table.Value;
import java.util.List;
import java.util.Map;

public interface DstTables {

  /**
   * Create a new table by the given table specification.
   *
   * @param table The specification of the table that will be created.
   */
  void createTable(TableSpecification table);

  /**
   * Append a list of records to a table.
   *
   * @param tableName The table's name to which the records will be appended.
   * @param records The records that will be append to the table.
   */
  void append(String tableName, List<Record> records);

  /**
   * Get the table specification of the given `tableName`.
   *
   * @param tableName The table's name , which need to be get the specification.
   */
  TableSpecification getTableSpecification(String tableName);

  /**
   * Query target Records by the given `tableName` and `conditions`.
   *
   * @param tableName  The table's name , which need to be queried.
   * @param conditions Typically, the query condition is the value of
   *                   the corresponding field of the query table,
   *                   the conditions can be null
   */
  List<Record> query(String tableName, Map<Field, Value> conditions);

  /**
   * Drop a table from store by the given `tableName`.
   *
   * @param tableName The table's name which will be drop.
   * @return True if we succeeded to drop the table, otherwise is false.
   */
  Status drop(String tableName);


  /**
   * Clear all the records of the table.
   *
   * @param tableName The table's name to which will be clean.
   */
  void clearTable(String tableName);

  /**
   * Clear the whole table store.
   */
  void clear();

}
