package com.distkv.dst.common;

public class DstTuple<X, Y> {

  private X firstElement;

  private Y secondElement;

  public DstTuple(X firstElement, Y secondElement) {
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
