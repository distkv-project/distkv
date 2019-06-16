package org.dst.core.operatorImpl;

import org.dst.core.operatorset.DstList;

import java.util.HashMap;
import java.util.List;

public class DstListImpl implements DstList {

    private HashMap<String, List<String>> listMap;

    public DstListImpl() {
        this.listMap = new HashMap<String, List<String>>();
    }

    @Override
    public void put(String Key, List<String> Value) {
        listMap.put(Key, Value);
    }

    @Override
    public List<String> get(String Key) {
        return listMap.get(Key);
    }

    @Override
    public boolean del(String Key) {
        if(!listMap.containsKey(Key)) {
            return false;
        }

        listMap.remove(Key);
        return true;
    }

    @Override
    public boolean lput(String Key, List<String> Value) {
        return false;
    }

    @Override
    public boolean rput(String Key, List<String> Value) {
        return false;
    }

    @Override
    public boolean ldel(String Key, int n) {
        return false;
    }

    @Override
    public boolean rdel(String Key, int n) {
        return false;
    }
}
