package test.org.dst.core.operator;

import com.google.common.collect.ImmutableList;
import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.dst.core.table.Field;
import org.dst.core.table.TableSpecification;
import org.dst.core.table.ValueType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class KVSTableTest {

  private static List<Field> dummyTableSpecificationFields() {
    Field primaryField = new Field.Builder()
          .name("primaryIntField")
          .primary(true)
          .type(ValueType.INT)
          .index(false)
          .build();
    Field indexField = new Field.Builder()
          .name("indexStrField")
          .primary(false)
          .type(ValueType.STRING)
          .index(true)
          .build();
    Field ordinaryField = new Field.Builder()
          .name("ordinaryDoubleField")
          .primary(false)
          .type(ValueType.STRING_LIST)
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

  private static List<Field> dummyTableEntryData() {
    return null;
  }

  @Test
  public void testCreateTableAndFindTable() {
    KVStore store = new KVStoreImpl();
    TableSpecification tableSpecification = new TableSpecification
          .Builder()
          .name("testTable")
          .fields(dummyTableSpecificationFields())
          .build();
    store.tables().createTable(tableSpecification);
    TableSpecification testTable = store.tables().findTableSpecification("testTable");
    Assert.assertEquals(testTable, tableSpecification);
  }

  @Test
  public void testAppend() {
    KVStore store = new KVStoreImpl();
    TableSpecification tableSpecification = new TableSpecification
          .Builder()
          .name("testTable")
          .fields(dummyTableSpecificationFields())
          .build();
    store.tables().createTable(tableSpecification);


  }


}
