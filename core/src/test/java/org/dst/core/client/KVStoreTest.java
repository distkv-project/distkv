package org.dst.core.client;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import org.dst.core.KVStoreImpl;
import org.dst.core.KVStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KVStoreTest {

    @Test
    public void testKVStore_Str() {
        KVStore ks = new KVStoreImpl();
        ks.str().put("k1", "v1");
        ks.str().put("k2", "v2");
        Assertions.assertEquals("v1", ks.str().get("k1"));
        Assertions.assertEquals("v2", ks.str().get("k2"));
        Assertions.assertEquals(true, ks.str().del("k1"));
        Assertions.assertEquals(null, ks.str().get("k1"));
    }

    @Test
    public void TestKVStore_List() {
        KVStore ks = new KVStoreImpl();
        List<String> list = new ArrayList<String>();
        list.add("v1");
        list.add("v2");
        list.add("v3");
        ks.list().put("k1", list);
        Assertions.assertEquals(list, ks.list().get("k1"));
        Assertions.assertEquals(true, ks.list().del("k1"));
        Assertions.assertEquals(null, ks.list().get("k1"));
    }

    @Test
    public void TestKVStore_Set() {
        KVStore ks = new KVStoreImpl();
        Set<String> set = new HashSet<String>();
        set.add("v1");
        set.add("v2");
        set.add("v3");
        ks.set().put("k1", set);
        Assertions.assertEquals(set, ks.set().get("k1"));
        Assertions.assertEquals(true, ks.set().exists("k1", "v3"));
        Assertions.assertEquals(true, ks.set().del("k1"));
        Assertions.assertEquals(null, ks.set().get("k1"));
    }

    @Test
    public void TestKVStore_Dict() {
        KVStore ks = new KVStoreImpl();
        Map<String, String> dict = new HashMap<String, String>();
        dict.put("k1", "v1");
        dict.put("k2", "v2");
        dict.put("k3", "v3");
        ks.dict().put("k1", dict);
        Assertions.assertEquals(dict, ks.dict().get("k1"));
        Assertions.assertEquals("v2", ks.dict().get("k1", "k2"));
        Assertions.assertEquals(true, ks.dict().del("k1"));
        Assertions.assertEquals(null, ks.dict().get("k1"));
    }

    @Test
    public void TestKVStore_Table() {
    }

}
