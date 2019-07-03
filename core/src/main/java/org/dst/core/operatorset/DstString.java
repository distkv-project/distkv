package org.dst.core.operatorset;

public interface DstString {

  /**
   * This method will put a key-value pair to map
   *
   * @param Key   the key to store
   * @param Value the string value to store
   */
  public void put(String Key, String Value);

  /**
   * This method will query a string value based on the key
   *
   * @param Key Obtain a string value based on the key
   * @return the string  value
   */
  public String get(String Key);

  /**
   * This method will delete a string value based on the key
   *
   * @param Key delete a key-value pair based on the key
   * @return true or false, indicates that the deletion succeeded or failed.
   */
  public boolean del(String Key);

}
