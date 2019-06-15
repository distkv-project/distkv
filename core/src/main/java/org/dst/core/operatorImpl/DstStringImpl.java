package org.dst.core.operatorImpl;

import java.util.HashMap;
import org.dst.core.operatorset.DstString;

public class DstStringImpl implements DstString {

    private HashMap<String, String> strMap;
	
    public DstStringImpl() {
        this.strMap = new HashMap<String, String>();
    }
	
    @Override
    public void put(String  Key, String Value) {
        strMap.put(Key, Value);
    }

    @Override
    public String get(String Key) {
        return strMap.get(Key);
    }

    @Override
    public boolean del(String Key) {
        if(!strMap.containsKey(Key)) {
            return false;
        }

        strMap.remove(Key);
        return true;
    }
	
}
