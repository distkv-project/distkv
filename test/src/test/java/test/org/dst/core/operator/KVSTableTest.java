package test.org.dst.core.operator;

import com.google.common.collect.ImmutableList;
import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.dst.core.table.Field;
import org.dst.core.table.TableSpecification;
import org.dst.core.table.Value;
import org.dst.core.table.ValueType;
import org.dst.core.table.StrListValue;
import org.dst.core.table.StrValue;
import org.dst.core.table.Record;
import org.dst.core.table.IntValue;
import org.dst.exception.TableNotFoundException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KVSTableTest {

  private static final KVStore store = new KVStoreImpl();

  private static final String TEST_TABLE = "testTable";

  private static List<Field> dummyTableSpecificationFields() {
    Field primaryField = new Field.Builder()
          .name("primaryField")
          .primary(true)
          .type(ValueType.INT)
          .index(false)
          .build();
    Field indexField = new Field.Builder()
          .name("indexField")
          .primary(false)
          .type(ValueType.STRING)
          .index(true)
          .build();
    Field ordinaryField = new Field.Builder()
          .name("ordinaryField")
          .primary(false)
          .type(ValueType.STRING)
          .index(false)
          .build();
    Field ordinaryStrListField = new Field.Builder()
          .name("ordinaryStrListField")
          .primary(false)
          .type(ValueType.STRING_LIST)
          .index(false)
          .build();
    return ImmutableList.of(primaryField, indexField, ordinaryField, ordinaryStrListField);
  }


  private void dummyCreateTable() {
    store.tables().clear();
    TableSpecification tableSpecification = new TableSpecification
          .Builder()
          .name(TEST_TABLE)
          .fields(dummyTableSpecificationFields())
          .build();
    store.tables().createTable(tableSpecification);
  }

  private static List<Record> dummyTableEntryData() {
    Value primaryValue = new IntValue(1);
    Value indexValue = new StrValue("1111");
    Value ordinaryValue = new StrValue("ordinaryValue");
    Value strListValue = new StrListValue(ImmutableList.of("aaa", "bbb", "ccc", "ddd"));

    Record record = new Record();
    List<Value> values = new ArrayList<>();
    // orderly addition
    values.add(primaryValue);
    values.add(indexValue);
    values.add(ordinaryValue);
    values.add(strListValue);
    record.setRecord(values);

    return ImmutableList.of(record);
  }


  @Test(priority = 1)
  public void testFindTableSpecification() {
    dummyCreateTable();
    TableSpecification testTable = store.tables().findTableSpecification(TEST_TABLE);
    Assert.assertEquals(testTable.getName(), TEST_TABLE);
  }

  @Test(priority = 2)
  public void testAppendAndQuery() {
    List<Record> records = dummyTableEntryData();
    store.tables().append(TEST_TABLE, records);

    List<Record> queryData = store.tables().query(TEST_TABLE, null);
    Assert.assertEquals(queryData.size(), 1);
  }

  @Test(priority = 3,dependsOnMethods = "testAppendAndQuery")
  public void testQueryByConditions() {
    Map<Field, Value> conditions = new HashMap<>();
    Field primaryField = new Field.Builder()
          .name("primaryField")
          .primary(true)
          .type(ValueType.INT)
          .index(false)
          .build();
    Value primaryValue = new IntValue(1);
    conditions.put(primaryField, primaryValue);

    List<Record> queryData = store.tables().query(TEST_TABLE, conditions);
    Assert.assertEquals(1, queryData.size());
  }

  @Test(priority = 4,expectedExceptions = TableNotFoundException.class)
  public void testClear() {
    store.tables().clear();
    store.tables().findTableSpecification(TEST_TABLE);
  }

  @Test(priority = 5)
  public void testClearTable() {
    dummyCreateTable();
    store.tables().append(TEST_TABLE, dummyTableEntryData());
    store.tables().clearTable(TEST_TABLE);
    List<Record> queryData = store.tables().query(TEST_TABLE, null);
    Assert.assertEquals(0, queryData.size());
  }

  @Test(priority = 6,expectedExceptions = TableNotFoundException.class)
  public void testDrop() {
    dummyCreateTable();
    store.tables().append(TEST_TABLE, dummyTableEntryData());
    store.tables().drop(TEST_TABLE);
    store.tables().findTableSpecification(TEST_TABLE);
  }
}
