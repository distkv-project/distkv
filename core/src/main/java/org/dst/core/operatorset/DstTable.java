package org.dst.core.operatorset;

import org.dst.core.table.FieldValue;
import org.dst.core.table.RecordEntry;
import org.dst.core.table.TableSpecification;
import java.util.List;

public interface DstTable {

  /**
   * This method will create a new table
   *
   * @param table the key to store
   */
  void createTable(TableSpecification table);

  /**
   * This method will append content into table
   *
   * @param recordEntry key & values
   */
  void append(RecordEntry recordEntry);

  /**
   * This method will return table info by table name
   *
   * @param name table name
   */
  TableSpecification getTableByName(String name);

  /**
   * This method will return all values by TableSpecification
   *
   * @param table table
   */
  List<FieldValue> query(TableSpecification table);

  /**
   * This will return values from table store by fields in TableSpecification
   * @param table table
   */
  List<FieldValue> queryByConditions(TableSpecification table, FieldValue... fields);

  /**
   * This method will drop table from store by table
   *
   * @param table TableSpecification
   */
  boolean delete(TableSpecification table);

  /**
   * This method will clear the whole table store
   */
  void clear();

}
