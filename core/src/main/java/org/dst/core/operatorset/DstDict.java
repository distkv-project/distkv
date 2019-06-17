package org.dst.core.operatorset;

import java.util.Map;

public interface DstDict {

    /**
     * This method will put a key-value pair to map
     * @param Key  the key to store
     * @param Value the dictionary value to store
     */
    public void put(String Key, Map<String, String> Value);

    /**
     * This method will query a dictionary value based on the key
     * @param Key Obtain a dictionary value based on the key
     * @return the dictionary value
     */
    public Map<String, String> get(String Key);

    /**
     * This method will delete a dictionary value based on the key
     * @param Key delete a key-value pair based on the key
     * @return true or false, indicates that the deletion succeeded or failed.
     */
    public boolean del(String Key);

}

