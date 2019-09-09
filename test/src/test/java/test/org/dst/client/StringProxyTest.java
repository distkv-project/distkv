package test.org.dst.client;

import com.baidu.brpc.client.RpcCallback;
import org.dst.client.DefaultDstClient;
import org.dst.client.DstClient;
import org.dst.exception.KeyNotFoundException;
import org.dst.server.generated.StringProtocol;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.org.dst.supplier.BaseTestSupplier;

public class StringProxyTest extends BaseTestSupplier {

  private static final String serverAddress = "list://127.0.0.1:8082";

  @Test
  public void testPutAndGet() {
    DstClient client = new DefaultDstClient(serverAddress);
    client.strs().put("k1", "v1");
    Assert.assertEquals("v1", client.strs().get("k1"));
  }

  @Test
  public void testKeyNotFoundWhenGetting() {
    DstClient client = new DefaultDstClient(serverAddress);
    try {
      client.strs().get("k1");
      Assert.fail("It shouldn't reach here.");
    } catch (KeyNotFoundException e) {
      Assert.assertTrue(true);
    }
  }

  @Test
  public void testAsyncStringGet() {
    DstClient client = new DefaultDstClient(serverAddress);
    client.strs().put("k1", "v1");

    client.strs().AsynGet("k1", new RpcCallback<StringProtocol.GetResponse>() {
      @Override
      public void success(StringProtocol.GetResponse getResponse) {
        Assert.assertEquals("v1",getResponse.getValue());
      }

      @Override
      public void fail(Throwable throwable) {

      }
    });
  }
}
