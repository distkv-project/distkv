package org.dst.core.operatorImpl;

import org.dst.core.exception.NotImplementException;
import org.dst.core.operatorset.DstTable;
import org.dst.core.table.FieldSpecification;
import org.dst.core.table.FieldValue;
import org.dst.core.table.IndexEntry;
import org.dst.core.table.RecordEntry;
import org.dst.core.table.TableSpecification;
import org.dst.exception.RepeatCreateTableException;
import org.dst.exception.TableNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //TODO(tansen)???  Map的key不可以重复，所以，key不能存对应的字段值。 可以考虑将key和value进行翻转.利用其他策略实现存储重复的key
    TableSpecification spec = recordEntry.getTableSpec();
    if (!isExist(spec)) {
      throw new TableNotFoundException(recordEntry.getTableSpec().getName());
    }
    RecordEntry store = tableMap.get(spec.getName());
    List<List<FieldValue>> newValues = recordEntry.getFieldValues();
    List<List<FieldValue>> oldValues = store.getFieldValues();

    if (oldValues == null) {
      store.setFieldValues(newValues);
    } else {
      oldValues.addAll(newValues);
    }

    int size = newValues.size();
    int oldSize = oldValues.size();
    List<FieldSpecification> fields = spec.getFields();

    //add index & primary map
    for (FieldSpecification field : fields) {
      boolean primary = field.isPrimary();
      boolean index = field.isIndex();
      if (primary || index) {
        for (int i = 0; i < size; i++) {
          List<FieldValue> newValue = newValues.get(i);
          int s = newValue.size();
          for (int j = 0; j < s; j++) {
            if (primary) {
              Map<FieldValue, Integer> primarys = store.getPrimarys();
              if (primarys != null) {
                if (newValue.get(i).index == field.index) {
                  primarys.put(newValue.get(i), (oldSize + i + 1));
                }
              } else {
                Map<FieldValue, Integer> newPrimarys = new HashMap<>();
                newPrimarys.put(newValue.get(i), (oldSize + i + 1));
                store.setPrimarys(newPrimarys);
              }
            }
            if (index) {
              if (newValue.get(i).index == field.index) {
                IndexEntry indexEntry = store.getIndexEntry();
                if (indexEntry != null) {
                  if (newValue.get(i).index == indexEntry.getFieldIndex()) {
                    indexEntry.getIndexs().put(newValue.get(i), (oldSize + i + 1));
                  }
                } else {
                  if (newValue.get(i).index == field.index) {
                    IndexEntry newIndexEntry = new IndexEntry();
                    newIndexEntry.setFieldIndex(field.index);
                    Map<FieldValue, Integer> indexs = new HashMap<>();
                    indexs.put(newValue.get(i), (oldSize + i + 1));
                    newIndexEntry.setIndexs(indexs);
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  @Override
  public TableSpecification getTableByName(String name) {
    if (tableMap.get(name) == null) {
      throw new TableNotFoundException(name);
    }
    return tableMap.get(name).getTableSpec();
  }

  @Override
  public List<List<FieldValue>> query(TableSpecification table, FieldValue... fields) {
    List<List<FieldValue>> result = new ArrayList<>();
    if (fields.length == 0) {
      return tableMap.get(table.getName()).getFieldValues();
    } else if (fields.length == 1) {
      RecordEntry recordEntry = tableMap.get(table.getName());
      List<FieldSpecification> targetFields = table.getFields();
      for (int j = 0; j < targetFields.size(); j++) {
        FieldSpecification field = targetFields.get(j);
        if (field.index == fields[0].getIndex() && field.isPrimary()) {
          Integer index = recordEntry.getPrimarys().get(fields[0]);
          List<FieldValue> fieldValues = recordEntry.getFieldValues().get(index);
          result.add(fieldValues);
        } else if (field.index == fields[0].getIndex() && field.isIndex()) {
          Integer index = recordEntry.getIndexEntry().getIndexs().get(fields[0]);
          List<FieldValue> fieldValues = recordEntry.getFieldValues().get(index);
          result.add(fieldValues);
        } else {
          List<List<FieldValue>> fieldValues = recordEntry.getFieldValues();
          int size = fieldValues.size();
          for (int i = 0; i < size; i++) {
            if (fieldValues.get(i).equals(fields[0])) {
              result.add(fieldValues.get(i));
            }
          }
        }
      }
    } else {
      //TODO(tansen)
    }
    return null;
  }

  @Override
  public RecordEntry drop(TableSpecification table) {
    return tableMap.remove(table.getName());
  }

  @Override
  public boolean verifyLegitimacy(RecordEntry recordEntry) {
    //TODO(tansen)
    throw new NotImplementException();
  }

  @Override
  public void clearTable(TableSpecification table) {
    RecordEntry recordEntry = tableMap.get(table.getName());
    recordEntry.getPrimarys().clear();
    recordEntry.setIndexEntry(null);
    recordEntry.getFieldValues().clear();
  }

  @Override
  public void clear() {
    tableMap.clear();
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
