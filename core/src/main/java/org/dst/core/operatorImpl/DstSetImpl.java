package org.dst.core.operatorImpl;

import org.dst.core.operatorset.DstSet;

import java.util.HashMap;
import java.util.Set;

public class DstSetImpl implements DstSet {

    private HashMap<String, Set<String>> setMap;

    public DstSetImpl() {
        this.setMap = new HashMap<String, Set<String>>();
    }

    @Override
    public void put(String Key, Set<String> Value) {
        setMap.put(Key, Value);
    }

    @Override
    public Set<String> get(String Key) {
        return setMap.get(Key);
    }

    @Override
    public boolean del(String Key) {
        if(!setMap.containsKey(Key)) {
            return false;
        }

        setMap.remove(Key);
        return true;
    }

    @Override
    public boolean exists(String Key, String Value) {
        if(!setMap.containsKey(Key)) {
            return false;
        }else {
            return setMap.get(Key).contains(Value);
        }
    }

}
