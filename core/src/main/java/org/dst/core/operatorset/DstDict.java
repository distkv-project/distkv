package org.dst.core.operatorset;

import java.util.Map;

public interface DstDict {

    public void put(String Key, Map<String, String> Value);

    public Map<String, String> get(String Key);

    public String get(String outKey, String inKey);

    public boolean del(String Key);

}

