package test.org.dst.core.operator;

import com.google.common.collect.ImmutableList;
import org.dst.core.table.FieldSpecification;
import org.dst.core.table.RecordEntry;
import org.dst.core.table.StringValue;
import org.dst.core.table.TableSpecification;
import org.dst.core.table.StringListValue;
import org.dst.core.table.IntValue;
import org.dst.core.table.FieldValue;
import org.dst.core.table.ValueTypeEnum;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class KVTableTest {

  Map<String, RecordEntry> store1 = new TreeMap<String, RecordEntry>();
  Map<TableSpecification, RecordEntry> store2 =
        new TreeMap<TableSpecification, RecordEntry>();

  @Test
  public void testTable() {
    //创建字段及其属性，是否有主键、是否是索引
    FieldSpecification field1 =
          new FieldSpecification(0, "username", ValueTypeEnum.STRING, false, false);
    FieldSpecification field2 =
          new FieldSpecification(1, "age", ValueTypeEnum.INT, false, false);
    FieldSpecification field3 =
          new FieldSpecification(2, "hobby", ValueTypeEnum.STRING_LIST, false, false);
    List<FieldSpecification> fields = new ArrayList<>();
    fields.add(field1);
    fields.add(field2);
    fields.add(field3);

    //创建表
    TableSpecification t1 = new TableSpecification("UserInfo", fields);
    //TableSpecification t2=new TableSpecification("Community",fields);

    RecordEntry re = new RecordEntry();
    re.setTableSpec(t1);
    String key = t1.getName();
    //创建表
    store1.put(key, re);


    //往表里放数据
    //根据表名获取该表的存储记录，
    store1.get(key);
    System.out.println("当前表字段信息：" +
          store1.get(key).getTableSpec().getFields());


    //生成一条记录的内容
    StringValue v1 = new StringValue(0, "檀森伢");
    IntValue v2 = new IntValue(1, 18);
    StringListValue v3 = new StringListValue(2,
          ImmutableList.of("吃饭", "睡觉", "打豆豆"));

    List<FieldValue> values = new ArrayList<>();
    values.add(v1);
    values.add(v2);
    values.add(v3);

    List<List<FieldValue>> records = new ArrayList<>();
    records.add(values);


    //往当前表记录插入数据
    store1.get(key).setFieldValues(records);

    System.out.println("当前表所有内容：" + store1.get(key));

    System.out.println("当前表的记录value信息：" + store1.get(key).getFieldValues());


  }


}
