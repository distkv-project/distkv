package org.dst.client.cmd;

public class ClientResult {
  public String result;

  public ClientResult() {
  }

  public ClientResult(String result) {
    this.result = result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  @Override
  public String toString() {
    return result;
  }
}
