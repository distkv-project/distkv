package com.distkv.common;

public class DistKVTuple<X, Y> {

  private X firstElement;

  private Y secondElement;

  public DistKVTuple(X firstElement, Y secondElement) {
    this.firstElement = firstElement;
    this.secondElement = secondElement;
  }

  public X getFirst() {
    return firstElement;
  }

  public Y getSecond() {
    return secondElement;
  }

}
