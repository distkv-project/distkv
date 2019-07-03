package org.dst.core.exception;

public class KeyNotFoundException extends RuntimeException {

  public KeyNotFoundException() {}

  /**
   * If you want to query a key that don't exist in map, this method
   * will show a messgae
   *
   * @param message the exception message to display
   */
  public KeyNotFoundException(String message) {
    super(message);
  }

}
