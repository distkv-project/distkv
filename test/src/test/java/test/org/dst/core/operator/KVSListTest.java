package test.org.dst.core.operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.dst.core.KVStoreImpl;
import org.dst.core.KVStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KVSListTest {

    @Test
    public void testPutAndGet() {
        KVStore ks = new KVStoreImpl();
        List<String> list = new ArrayList<>();
        list.add("v1");
        list.add("v2");
        list.add("v3");
        ks.list().put("k1", list);
        Assertions.assertEquals(list, ks.list().get("k1"));
    }

    @Test
    public void testDel() {
        KVStore ks = new KVStoreImpl();
        List<String> list = new ArrayList<>();
        list.add("v1");
        list.add("v2");
        list.add("v3");
        ks.list().put("k1", list);
        Assertions.assertEquals("ok",ks.list().del("k1").toString());
        Assertions.assertNull(ks.list().get("k1"));
    }

    @Test
    public void testLPut() {
        KVStore ks = new KVStoreImpl();
        List<String> list1 = new ArrayList<String>();
        list1.add("v1");
        ks.list().put("k1",list1);
        List<String> list2 = new ArrayList<>();
        list2.add("v2");
        list2.add("v3");
        ks.list().lput("k1",list2);
        Assertions.assertEquals(Arrays.asList("v2","v3","v1"),ks.list().get("k1"));
    }

    @Test
    public void testRPut() {
        KVStore ks = new KVStoreImpl();
        List<String> list1 = new ArrayList<String>();
        list1.add("v1");
        ks.list().put("k1",list1);
        List<String> list2 = new ArrayList<>();
        list2.add("v2");
        list2.add("v3");
        ks.list().rput("k1", list2);
        Assertions.assertEquals(Arrays.asList("v1","v2","v3"),ks.list().get("k1"));
    }


    @Test
    public void testLDel() {
        KVStore ks = new KVStoreImpl();
        List<String> list1 = new ArrayList<String>();
        list1.add("v1");
        list1.add("v2");
        list1.add("v3");
        ks.list().put("k1",list1);
        ks.list().ldel("k1",1);
        Assertions.assertEquals(Arrays.asList("v2","v3"),ks.list().get("k1"));
        Assertions.assertEquals("key not exist",ks.list().ldel("-k",1).toString());
    }


    @Test
    public void testRDel() {
        KVStore ks = new KVStoreImpl();
        List<String> list1 = new ArrayList<String>();
        list1.add("v1");
        list1.add("v2");
        list1.add("v3");
        ks.list().put("k1",list1);
        ks.list().rdel("k1",1);
        Assertions.assertEquals(Arrays.asList("v1","v2"),ks.list().get("k1"));
        Assertions.assertEquals("key not exist",ks.list().rdel("-k",1).toString());
    }

}
