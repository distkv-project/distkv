package org.dst.utils.enums;

/**
 *
 * <p>kvstore 处理返回结果</p>
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
