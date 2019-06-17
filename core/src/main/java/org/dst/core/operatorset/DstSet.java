package org.dst.core.operatorset;

import org.dst.core.exception.KeyNotFoundException;

import java.util.Set;

public interface DstSet {

    public void put(String Key, Set<String> Value);

    public Set<String> get(String Key);

    public boolean del(String Key);

    public boolean exists(String Key, String Value) throws KeyNotFoundException;

}
