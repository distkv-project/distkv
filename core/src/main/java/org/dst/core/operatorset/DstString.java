package org.dst.core.operatorset;

public interface DstString {
	
    public void put(String Key, String Value);
	
    public String get(String Key);
	
    public boolean del(String Key);
	
}
