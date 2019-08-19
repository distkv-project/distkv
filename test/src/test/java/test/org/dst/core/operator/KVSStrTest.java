package test.org.dst.core.operator;

import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class KVSStrTest {
    @Test
    public void testStr() {
        KVStore store = new KVStoreImpl();
        store.str().put("k1", "v1");
        store.str().put("k2", "v2");
        Assertions.assertEquals("v1", store.str().get("k1"));
        Assertions.assertEquals("v2", store.str().get("k2"));
        Assertions.assertTrue(store.str().del("k1"));
        Assertions.assertNull(store.str().get("k1"));
    }

}
