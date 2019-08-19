package test.org.dst.core.operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.dst.core.KVStoreImpl;
import org.dst.core.KVStore;
import org.dst.exception.KeyNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KVSListTest {

    private static List<String> listForKVSTest() {
        List<String> list = new ArrayList<>();
        list.add("v1");
        list.add("v2");
        list.add("v3");
        return list;
    }

    @Test
    public void testPutAndGet() {
        KVStore store = new KVStoreImpl();
        store.lists().put("k1", listForKVSTest());
        Assertions.assertEquals(listForKVSTest(), store.lists().get("k1"));

        Throwable exception = Assertions.assertThrows(KeyNotFoundException.class,()->{
            Assertions.assertEquals(listForKVSTest(), store.lists().get("k2"));
        });
        Assertions.assertEquals("The key k2 doesn't exist in the store.",exception.getMessage());
    }

    @Test()
    public void testDel() {
        KVStore store = new KVStoreImpl();
        store.lists().put("k1", listForKVSTest());
        store.lists().del("k1");
        Throwable exception = Assertions.assertThrows(KeyNotFoundException.class,()->{
            store.lists().get("k1");
        });
        Assertions.assertEquals("The key k1 doesn't exist in the store.",exception.getMessage());
    }

    @Test
    public void testLPut() {
        KVStore store = new KVStoreImpl();
        store.lists().put("k1",listForKVSTest());
        List<String> list2 = new ArrayList<>();
        list2.add("v4");
        list2.add("v5");
        store.lists().lput("k1",list2);
        Assertions.assertEquals(Arrays.asList("v4","v5","v1","v2","v3"),store.lists().get("k1"));

        Throwable exception = Assertions.assertThrows(KeyNotFoundException.class,()->{
            store.lists().lput("k2",list2);
        });
        Assertions.assertEquals("The key k2 doesn't exist in the store.",exception.getMessage());
    }

    @Test
    public void testRPut() {
        KVStore store = new KVStoreImpl();
        store.lists().put("k1",listForKVSTest());
        List<String> list2 = new ArrayList<>();
        list2.add("v4");
        list2.add("v5");
        store.lists().rput("k1", list2);
        Assertions.assertEquals(Arrays.asList("v1","v2","v3","v4","v5"),store.lists().get("k1"));

        Throwable exception = Assertions.assertThrows(KeyNotFoundException.class,()->{
            store.lists().rput("k2", list2);
        });
        Assertions.assertEquals("The key k2 doesn't exist in the store.",exception.getMessage());
    }

    @Test
    public void testLDel() {
        KVStore store = new KVStoreImpl();
        store.lists().put("k1",listForKVSTest());
        store.lists().ldel("k1",1);
        Assertions.assertEquals(Arrays.asList("v2","v3"),store.lists().get("k1"));

        Throwable exception = Assertions.assertThrows(KeyNotFoundException.class,()->{
            store.lists().ldel("k2",1);
        });
        Assertions.assertEquals("The key k2 doesn't exist in the store.",exception.getMessage());
    }

    @Test
    public void testRDel() {
        KVStore store = new KVStoreImpl();
        store.lists().put("k1",listForKVSTest());
        store.lists().rdel("k1",1);
        Assertions.assertEquals(Arrays.asList("v1","v2"),store.lists().get("k1"));

        Throwable exception = Assertions.assertThrows(KeyNotFoundException.class,()->{
            store.lists().rdel("k2",1);
        });
        Assertions.assertEquals("The key k2 doesn't exist in the store.",exception.getMessage());
    }

}
