package org.dst.core.operatorImpl;

import org.dst.core.operatorset.DstDict;

import java.util.HashMap;
import java.util.Map;

public class DstDictImpl implements DstDict {

    private HashMap<String, Map<String, String>> dictMap;

    public DstDictImpl() {
        this.dictMap = new HashMap<String, Map<String, String>>();
    }

    @Override
    public void put(String Key, Map<String, String> Value) {
        dictMap.put(Key, Value);
    }

    @Override
    public Map<String, String> get(String Key) {
        return dictMap.get(Key);
    }

    @Override
    public String get(String outKey, String inKey) {
        return dictMap.get(outKey).get(inKey);
    }

    @Override
    public boolean del(String Key) {
        if(!dictMap.containsKey(Key)) {
            return false;
        }

        dictMap.remove(Key);
        return true;
    }
}
