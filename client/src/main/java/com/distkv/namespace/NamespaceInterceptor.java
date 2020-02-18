package com.distkv.namespace;

/**
 * This is an interceptor to intercept requests and resolve namespace
 * on the client side.
 */
public class NamespaceInterceptor {

  private String namespace;

  public String getNamespace() {
    return namespace;
  }

  public  void active(String namespace) {
    this.namespace = namespace;
  }

  public  void deactive() {
    this.namespace = null;
  }

}
