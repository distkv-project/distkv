package test.org.dst.stress;

import org.testng.Assert;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;


public class TestRDSStress {

  @Test(threadPoolSize = 10, invocationCount = 10)
  public void testStringPut() {
    Jedis jedis = new Jedis("127.0.0.1",6379);
    int max = 100000;
    long start = System.currentTimeMillis();
    String name = Thread.currentThread().getName();
    for (long i = 0;i< max ; i++) {
      jedis.set(name + i, "test" + i);
    }
    String as = jedis.get(name+59999);
    long end = System.currentTimeMillis();
    System.out.println(end-start);
    Assert.assertEquals(as,"test59999");
    jedis.close();
  }

}
