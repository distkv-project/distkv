package org.dst.core.operatorImpl;

import org.dst.core.operatorset.DstTable;
import org.dst.core.table.*;
import org.dst.exception.IncorrectRecordFormatException;
import org.dst.exception.IncorrectTableFormatException;
import org.dst.exception.RepeatCreateTableException;
import org.dst.exception.TableNotFoundException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class DstTableImpl implements DstTable {

  private HashMap<String, TableEntry> tableMap;

  public DstTableImpl() {
    this.tableMap = new HashMap<String, TableEntry>();
  }

  @Override
  public void createTable(TableSpecification tableSpec) {
    checkFormatofTableSpecification(tableSpec);
    if (isExist(tableSpec.getName())) {
      throw new RepeatCreateTableException(tableSpec.getName());
    }
    TableEntry table = new TableEntry.Builder().tableSpec(tableSpec).builder();
    tableMap.put(tableSpec.getName(), table);
  }

  @Override
  public void append(String tableName, List<Record> records) {
    if (isExist(tableName)) {
      throw new TableNotFoundException(tableName);
    }
    TableEntry store = tableMap.get(tableName);
    checkFormatOfRecords(store, records);
    List<Record> oldRecords = store.getRecords();
    int position = -1;
    //append records
    if (oldRecords == null) {
      store.setRecords(records);
    } else {
      position = oldRecords.size();
      oldRecords.addAll(records);
    }
    //append index
    TableSpecification tableSpec = store.getTableSpec();
    List<Field> fields = tableSpec.getFields();
    int fieldsSize = fields.size();
    for (int i = 0; i < fieldsSize; i++) {
      boolean primary = fields.get(i).isPrimary();
      boolean index = fields.get(i).isIndex();
      if (primary || index) {
        int newRecordSize = records.size();
        Map<Value, List<Integer>> indexs = store.getIndex().getIndexs();
        for (int j = 0; j < newRecordSize; j++) {
          Value value = records.get(j).getRecord().get(i);
          position += 1;
          if (primary) {
            //TODO (senyer) how to enhance this code :Arrays.asList() ?
            indexs.put(value, Arrays.asList(position));
          }
          if (index) {
            if (indexs.containsKey(value)) {
              List<Integer> positions = indexs.get(value);
              positions.add(position);
            } else {
              indexs.put(value, Arrays.asList(position));
            }
          }
        }
      }
    }
  }

  @Override
  public TableSpecification findTableSpecification(String tableName) {
    if (isExist(tableName)) {
      throw new TableNotFoundException(tableName);
    }
    return tableMap.get(tableName).getTableSpec();
  }

  @Override
  public List<Record> query(String tableName, Map<Field, Value> conditions) {
    if (isExist(tableName)) {
      throw new TableNotFoundException(tableName);
    }
    List<Record> records = tableMap.get(tableName).getRecords();
    if (conditions.isEmpty()) {
      return records;
    }
    List<Integer> positions = new ArrayList<>();

    for (Map.Entry<Field, Value> entry : conditions.entrySet()) {
      Field field = entry.getKey();
      Value value = entry.getValue();
      boolean primary = field.isPrimary();
      boolean index = field.isIndex();
      if (primary || index) {
        Index indexs = tableMap.get(tableName).getIndex();
        List<Integer> currentPositions = indexs.getIndexs().get(value);
        if (positions.isEmpty()) {
          positions.addAll(currentPositions);
        } else {
          positions.retainAll(currentPositions);
        }
      } else {
        TableSpecification tableSpec = tableMap.get(tableName).getTableSpec();
        List<Field> fields = tableSpec.getFields();
        List<Integer> currentPositions = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
          if (fields.get(i).getName().equals(field.getName())) {
            int size = records.size();
            for (int j = 0; j < size; j++) {
              List<Value> record = records.get(i).getRecord();
              if (record.get(i).equals(value)) {
                currentPositions.add(j);
              }
            }
          }
        }
        positions.retainAll(currentPositions);
      }
    }

    List<Record> result = new ArrayList<>();
    for (Integer position : positions) {
      Record record = records.get(position);
      result.add(record);
    }
    return result;
  }

  @Override
  public boolean drop(String tableName) {
    if (isExist(tableName)) {
      throw new TableNotFoundException(tableName);
    }
    tableMap.remove(tableName);
    return true;
  }

  @Override
  public void clearTable(String tableName) {
    TableEntry table = tableMap.get(tableName);
    table.getIndex().getIndexs().clear();
    table.getRecords().clear();
  }

  @Override
  public void clear() {
    tableMap.clear();
  }

  /**
   * check the records's format
   * 1. field locations must correspond one to one
   * 2. records can't be empty
   * 3. primary must unique TODO (senyer)
   * @param records records
   * @return boolean
   */
  private void checkFormatOfRecords(TableEntry store, List<Record> records) {
    if (records.isEmpty()) {
      throw new IncorrectRecordFormatException();
    }
    TableSpecification tableSpec = store.getTableSpec();
    List<Field> fields = tableSpec.getFields();
    for (Field field : fields) {
      ValueType fieldType = field.getType();
      for (Record record : records) {
        List<Value> values = record.getRecord();
        for (Value value : values) {
          if (!fieldType.equals(value.getType())) {
            throw new IncorrectRecordFormatException();
          }
        }
      }
    }
  }

  /**
   * check format of tableSpecification
   *  1. field can't be both index and primary
   *  2. table name can't be empty
   *  3. at least one field
   *
   * @param tableSpec tableSpec
   * @return boolean
   */
  private void checkFormatofTableSpecification(TableSpecification tableSpec) {
    if (tableSpec.getName() == null) {
      throw new IncorrectTableFormatException();
    }
    List<Field> fields = tableSpec.getFields();
    if (fields.size() <= 0) {
      throw new IncorrectTableFormatException();
    }
    for (Field field: fields ) {
      if (field.isPrimary()&&field.isIndex()) {
        throw new IncorrectTableFormatException();
      }
    }
  }

  /**
   * Determine whether the table has been created
   *
   * @param tableName table description
   * @return exists or not exist
   */
  private boolean isExist(String tableName) {
    return tableMap.containsKey(tableName);
  }
}
