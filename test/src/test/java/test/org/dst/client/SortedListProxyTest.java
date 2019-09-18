package test.org.dst.client;

import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
import org.dst.entity.SortedListEntity;
import org.testng.annotations.Test;
import test.org.dst.supplier.BaseTestSupplier;

import java.util.LinkedList;


public class SortedListProxyTest extends BaseTestSupplier {
  private static final String serverAddress = "list://127.0.0.1:8082";

  @Test
  public void testMain() {
    testPut();
  }

  private void testPut() {
    DstClient client = new DefaultDstClient(serverAddress);
    LinkedList<SortedListEntity> list = new LinkedList<>();
    list.add(new SortedListEntity("xswl", 9));
    list.add(new SortedListEntity("wlll", 8));
    list.add(new SortedListEntity("fw", 9));
    list.add(new SortedListEntity("55", 6));

    client.sortedList().put("k1", list);
  }

}
