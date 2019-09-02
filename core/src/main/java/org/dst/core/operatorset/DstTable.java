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
   * @param fields support for conditional query
   */
  List<List<FieldValue>> query(TableSpecification table,FieldValue... fields);

  /**
   * This method will drop table from store by table
   *
   * @param table TableSpecification
   * @return value which been deleted
   */
  RecordEntry drop(TableSpecification table);


  /**
   * Verify the legitimacy of the incoming object
   * @param recordEntry recordEntry
   * @return is legitimacy
   */
  boolean verifyLegitimacy(RecordEntry recordEntry);

  /**
   * This method will clear the table by table description
   * @param table incoming object
   */
  void clearTable(TableSpecification table);

  /**
   * This method will clear the whole table store
   */
  void clear();

}
