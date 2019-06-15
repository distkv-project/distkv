package org.dst.core.client;


import org.dst.core.KVStoreImpl;
import org.dst.core.KVStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KVStoreTest {

    @Test
    public void TestKVStore(){
        KVStore ks = new KVStoreImpl();
        ks.str().put("k1", "v1");
        ks.str().put("k2", "v2");
        Assertions.assertEquals("v1", ks.str().get("k1"));
        Assertions.assertEquals("v2", ks.str().get("k2"));
        Assertions.assertEquals(true, ks.str().del("k1"));
        Assertions.assertEquals(null, ks.str().get("k1"));
    }
}
