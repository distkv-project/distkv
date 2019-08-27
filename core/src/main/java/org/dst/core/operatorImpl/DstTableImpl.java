package org.dst.core.operatorImpl;

import org.dst.core.table.FieldValue;
import org.dst.core.table.TableSpecification;
import org.dst.core.table.RecordEntry;
import org.dst.core.exception.NotImplementException;
import org.dst.core.operatorset.DstTable;
import org.dst.exception.RepeatCreateTableException;
import org.dst.exception.TableNotFoundException;

import java.util.HashMap;
import java.util.List;

public class DstTableImpl implements DstTable {
  private HashMap<String, RecordEntry> tableMap;

  public DstTableImpl() {
    this.tableMap = new HashMap<String, RecordEntry>();
  }

  @Override
  public void createTable(TableSpecification tableSpec) {
    if (isExist(tableSpec)) {
      throw new RepeatCreateTableException(tableSpec.getName());
    }
    RecordEntry re = new RecordEntry();
    re.setTableSpec(tableSpec);
    tableMap.put(tableSpec.getName(), re);
  }

  @Override
  public void append(RecordEntry recordEntry) {
    if (isExist(recordEntry.getTableSpec())) {
      throw new TableNotFoundException(recordEntry.getTableSpec().getName());
    }
    TableSpecification ts=recordEntry.getTableSpec();
    recordEntry.getIndexEntry();
    RecordEntry re = tableMap.get(recordEntry.getTableSpec().getName());

  }

  @Override
  public TableSpecification getTableByName(String name) {
    throw new NotImplementException();
  }

  @Override
  public List<FieldValue> query(TableSpecification table) {
    throw new NotImplementException();
  }

  @Override
  public List<FieldValue> queryByConditions(TableSpecification table, FieldValue... fields) {
    throw new NotImplementException();
  }

  @Override
  public boolean delete(TableSpecification table) {
    throw new NotImplementException();
  }

  @Override
  public void clear() {
    throw new NotImplementException();
  }

  /**
   * Determine whether the table has been created
   *
   * @param tableSpec table description
   * @return exists or not exist
   */
  public boolean isExist(TableSpecification tableSpec) {
    return tableMap.get(tableSpec.name) != null;
  }
}
