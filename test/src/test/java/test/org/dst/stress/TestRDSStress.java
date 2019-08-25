package test.org.dst.stress;

import org.testng.Assert;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;


public class TestRDSStress {

  @Test(threadPoolSize = 5, invocationCount = 10)
  public void testStringPut() {
    Jedis jedis = new Jedis("127.0.0.1",6379);
    int max = 100000;
    long start = System.currentTimeMillis();
    for (long i = 0;i< max ; i++) {
      jedis.set(start+"RDS"+i,"testV"+(i-1));
    }
    String as = jedis.get(start+"RDS"+50000);
    long end = System.currentTimeMillis();
    System.out.println(end-start);
    Assert.assertEquals(as,"testV49999");
    jedis.close();
  }

}
