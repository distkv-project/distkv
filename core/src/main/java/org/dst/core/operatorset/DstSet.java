package org.dst.core.operatorset;

import org.dst.core.exception.KeyNotFoundException;

import java.util.Set;

public interface DstSet {

    /**
     * This method will put a key-value pair to map
     * @param Key  the key to store
     * @param Value the set value to store
     */
    public void put(String Key, Set<String> Value);

    /**
     * This method will query a set value based on the key
     * @param Key Obtain a set value based on the key
     * @return the set value
     */
    public Set<String> get(String Key);

    /**
     * This method will delete a set value based on the key
     * @param Key delete a key-value pair based on the key
     * @return true or false, indicates that the deletion succeeded or failed.
     */
    public boolean del(String Key);

    /**
     * This method will judge that if the value exists in map or not.
     * @param Key  the key exists in map
     * @param Value the set value you want to judge
     * @return true or false, indicates that the value exists or not.
     * @throws KeyNotFoundException If the key don't exist in map
     */
    public boolean exists(String Key, String Value) throws KeyNotFoundException;

}
