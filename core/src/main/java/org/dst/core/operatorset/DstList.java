package org.dst.core.operatorset;

import java.util.List;

public interface DstList {

    public void put(String Key, List<String> Value);

    public List<String>  get(String Key);

    public boolean del(String Key);

    //insert value from the left of list
    public boolean lput(String Key, List<String> Value);

    //insert value from the right of list
    public boolean rput(String Key, List<String> Value);

    //delete n values from the left of list
    public boolean ldel(String Key, int n);

    //delete n values from the right of list
    public boolean rdel(String Key, int n);

}
