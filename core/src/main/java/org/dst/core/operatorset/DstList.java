package org.dst.core.operatorset;

import org.dst.core.exception.NotImplementException;

import java.util.List;

public interface DstList {

    public void put(String Key, List<String> Value);

    public List<String>  get(String Key);

    public boolean del(String Key);

    //insert value from the left of list
    public boolean lput(String Key, List<String> Value) throws NotImplementException;

    //insert value from the right of list
    public boolean rput(String Key, List<String> Value) throws NotImplementException;

    //delete n values from the left of list
    public boolean ldel(String Key, int n) throws NotImplementException;

    //delete n values from the right of list
    public boolean rdel(String Key, int n) throws NotImplementException;

}
