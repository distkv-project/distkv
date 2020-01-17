package com.distkv.common.utils;

/**
 * The status is used to describe the result of server.
 */
public enum Status {

  KEY_NOT_FOUND("key not exist"),

  OK("ok");

  private String text;

  Status(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return this.text;
  }
}
