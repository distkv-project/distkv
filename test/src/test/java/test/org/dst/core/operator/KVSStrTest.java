package test.org.dst.core.operator;

import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class KVSStrTest {
    @Test
    public void testStr() {
        KVStore ks = new KVStoreImpl();
        ks.str().put("k1", "v1");
        ks.str().put("k2", "v2");
        Assertions.assertEquals("v1", ks.str().get("k1"));
        Assertions.assertEquals("v2", ks.str().get("k2"));
        Assertions.assertTrue(ks.str().del("k1"));
        Assertions.assertNull(ks.str().get("k1"));
    }

}
