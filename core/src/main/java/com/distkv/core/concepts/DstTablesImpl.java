package com.distkv.core.concepts;

import com.distkv.common.exception.TableNotFoundException;
import com.distkv.common.exception.DuplicatedPrimaryKeyException;
import com.distkv.common.exception.IncorrectRecordFormatException;
import com.distkv.common.exception.IncorrectTableFormatException;
import com.distkv.common.exception.TableAlreadyExistsException;
import com.distkv.core.DstHashMapImpl;
import com.distkv.core.table.Field;
import com.distkv.core.table.Index;
import com.distkv.core.table.Record;
import com.distkv.core.table.TableEntry;
import com.distkv.core.table.TableSpecification;
import com.distkv.core.table.Value;
import com.distkv.core.table.ValueType;

import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class DstTablesImpl extends DstConcepts<TableEntry> implements DstTables {

  public DstTablesImpl() {
    dstKeyValueMap = new DstHashMapImpl<>();
  }

  @Override
  public void createTable(TableSpecification tableSpec) {
    checkTableSpecificationFormat(tableSpec);
    checkTableAlreadyExist(tableSpec.getName());
    TableEntry table = new TableEntry.Builder().tableSpec(tableSpec).builder();
    dstKeyValueMap.put(tableSpec.getName(), table);
  }

  @Override
  public void append(String tableName, List<Record> sourceRecords) {
    List<Record> records = new ArrayList<>(sourceRecords);
    checkTableExists(tableName);
    TableEntry tableEntry = dstKeyValueMap.get(tableName);
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
      final boolean isPrimary = fields.get(i).isPrimary();
      final boolean isIndex = fields.get(i).isIndex();
      if (isPrimary || isIndex) {
        int newRecordSize = records.size();
        Map<Value, List<Integer>> indexs = tableEntry.getIndex().getIndexs();
        for (int j = 0; j < newRecordSize; j++) {
          Value value = records.get(j).getRecord().get(i);
          position += 1;
          if (isPrimary) {
            //TODO (senyer) how to enhance this code :Arrays.asList() ?
            indexs.put(value, Arrays.asList(position));
          }
          if (isIndex) {
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
    return dstKeyValueMap.get(tableName).getTableSpec();
  }

  @Override
  public List<Record> query(String tableName, Map<Field, Value> conditions) {
    checkTableExists(tableName);
    List<Record> records = dstKeyValueMap.get(tableName).getRecords();
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
        Index indexs = dstKeyValueMap.get(tableName).getIndex();
        List<Integer> currentPositions = indexs.getIndexs().get(value);
        if (positions.isEmpty()) {
          positions.addAll(currentPositions);
        } else {
          positions.retainAll(currentPositions);
        }
      } else {
        TableSpecification tableSpec = dstKeyValueMap.get(tableName).getTableSpec();
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
  public void clearTable(String tableName) {
    TableEntry table = dstKeyValueMap.get(tableName);
    table.getIndex().getIndexs().clear();
    table.getRecords().clear();
  }

  @Override
  public void clear() {
    dstKeyValueMap.clear();
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
   * Check if the table exists，if exist it will throw a TableAlreadyExistsException
   */
  private void checkTableAlreadyExist(String tableName) {
    if (isExist(tableName)) {
      throw new TableAlreadyExistsException(tableName);
    }
  }

  /**
   * Determine whether the table has been created
   *
   * @param tableName table description
   * @return exists or not exist
   */
  private boolean isExist(String tableName) {
    return dstKeyValueMap.containsKey(tableName);
  }
}
