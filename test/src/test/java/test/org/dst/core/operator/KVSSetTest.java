package test.org.dst.core.operator;

import org.dst.core.KVStore;
import org.dst.core.KVStoreImpl;
import org.dst.exception.KeyNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;


public class KVSSetTest {

    @Test
    public void testSet() {
        KVStore store = new KVStoreImpl();
        Set<String> set = new HashSet<>();
        set.add("v1");
        set.add("v2");
        set.add("v3");
        store.sets().put("k1", set);
        Assertions.assertEquals(set, store.sets().get("k1"));
        try {
            Assertions.assertTrue(store.sets().exists("k1", "v3"));
        } catch (KeyNotFoundException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(store.sets().del("k1"));
        Assertions.assertNull(store.sets().get("k1"));
    }
}
