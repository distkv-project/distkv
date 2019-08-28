package org.dst.core.operatorImpl;

import org.dst.core.table.*;
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
    TableSpecification spec = recordEntry.getTableSpec();
    if (!isExist(spec)) {
      throw new TableNotFoundException(recordEntry.getTableSpec().getName());
    }
    RecordEntry store = tableMap.get(spec.getName());
    List<List<FieldValue>> newValues = recordEntry.getFieldValues();
    if (store.getFieldValues() == null) {
      store.setFieldValues(newValues);
    } else {
      store.getFieldValues().addAll(newValues);
    }

    List<FieldSpecification> fields = spec.getFields();

    //插入索引信息
    for (FieldSpecification field : fields) {
      //TODO (tansen)
      boolean primary = field.isPrimary();
      if (primary) {
        for(List<FieldValue> fieldValues : newValues) {
          for (FieldValue fieldValue : fieldValues){
            if (fieldValue.index == field.index) {
              //store.getPrimarys().put(fieldValue,(store.getFieldValues()));
            }
          }

        }
      }

      //TODO (tansen)
      boolean index = field.isIndex();
      if (index) {
        for(List<FieldValue> fieldValues : newValues) {
          for (FieldValue fieldValue : fieldValues){
            if (fieldValue.index == field.index) {
              //store.getIndexEntry().put(fieldValue,(store.getFieldValues().size()+1));
            }
          }

        }
      }

    }
  }

  @Override
  public TableSpecification getTableByName(String name) {
    throw new NotImplementException();
  }

  @Override
  public List<FieldValue> query(TableSpecification table, FieldValue... fields) {
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
