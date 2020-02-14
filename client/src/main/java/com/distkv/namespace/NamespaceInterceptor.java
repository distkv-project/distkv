package com.distkv.namespace;

/**
 * This is a interceptor to intercept requests and resolve namespace
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

}
