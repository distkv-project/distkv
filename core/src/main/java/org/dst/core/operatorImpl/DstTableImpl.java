package org.dst.core.operatorImpl;

import org.dst.common.exception.*;
import org.dst.core.operatorset.DstTable;
import org.dst.core.table.TableEntry;
import org.dst.core.table.TableSpecification;
import org.dst.core.table.Record;
import org.dst.core.table.Value;
import org.dst.core.table.Field;
import org.dst.core.table.Index;
import org.dst.core.table.ValueType;
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
    checkTableSpecificationFormat(tableSpec);
    if (isExist(tableSpec.getName())) {
      throw new TableAlreadyExistsException(tableSpec.getName());
    }
    TableEntry table = new TableEntry.Builder().tableSpec(tableSpec).builder();
    tableMap.put(tableSpec.getName(), table);
  }

  @Override
  public void append(String tableName, List<Record> sourceRecords) {
    List<Record> records = new ArrayList<>(sourceRecords);
    checkTableExists(tableName);
    TableEntry tableEntry = tableMap.get(tableName);
    checkRecordsFormat(tableEntry, records);
    List<Record> oldRecords = tableEntry.getRecords();
    int position = -1;
    //append records
    if (oldRecords == null || oldRecords.size() <= 0) {
      tableEntry.setRecords(records);
    } else {
      position = oldRecords.size();
      oldRecords.addAll(records);
    }
    //append index
    TableSpecification tableSpec = tableEntry.getTableSpec();
    List<Field> fields = tableSpec.getFields();
    int fieldsSize = fields.size();
    for (int i = 0; i < fieldsSize; i++) {
      boolean primary = fields.get(i).isPrimary();
      boolean index = fields.get(i).isIndex();
      if (primary || index) {
        int newRecordSize = records.size();
        Map<Value, List<Integer>> indexs = tableEntry.getIndex().getIndexs();
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
  public TableSpecification getTableSpecification(String tableName) {
    checkTableExists(tableName);
    return tableMap.get(tableName).getTableSpec();
  }

  @Override
  public List<Record> query(String tableName, Map<Field, Value> conditions) {
    checkTableExists(tableName);
    List<Record> records = tableMap.get(tableName).getRecords();
    if (conditions == null || conditions.isEmpty()) {
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
    checkTableExists(tableName);
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
   * 3. primary must unique
   *
   * @param records records
   * @return boolean
   */
  private void checkRecordsFormat(TableEntry store, List<Record> records) {
    if (records.isEmpty()) {
      throw new IncorrectRecordFormatException(store.getTableSpec().getName());
    }
    TableSpecification tableSpec = store.getTableSpec();
    Map<Value, List<Integer>> indexs = store.getIndex().getIndexs();
    List<Field> fields = tableSpec.getFields();
    for (int i = 0; i < fields.size(); i++) {
      ValueType fieldType = fields.get(i).getType();
      for (Record record : records) {
        List<Value> values = record.getRecord();
        Value value = values.get(i);
        if (value != null) {
          if (!fieldType.equals(value.getType())) {
            throw new IncorrectRecordFormatException(store.getTableSpec().getName());
          }
        }
        //primary must unique
        if (fields.get(i).isPrimary()) {
          if (indexs.containsKey(value)) {
            throw new DuplicatedPrimaryKeyException(fields.get(i).getName());
          }
        }
      }
    }
  }

  /**
   * check format of tableSpecification
   * 1. field can't be both index and primary
   * 2. table name can't be empty
   * 3. at least one field
   *
   * @param tableSpec tableSpec
   * @return boolean
   */
  private void checkTableSpecificationFormat(TableSpecification tableSpec) {
    if (tableSpec.getName() == null) {
      throw new IncorrectTableFormatException(null);
    }
    List<Field> fields = tableSpec.getFields();
    if (fields.size() <= 0) {
      throw new IncorrectTableFormatException(tableSpec.getName());
    }
    for (Field field : fields) {
      if (field.isPrimary() && field.isIndex()) {
        throw new IncorrectTableFormatException(tableSpec.getName());
      }
    }
  }

  /**
   * Check if the table exists，if not exist it will throw a TableNotFoundException
   */
  private void checkTableExists(String tableName) {
    if (!isExist(tableName)) {
      throw new TableNotFoundException(tableName);
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
