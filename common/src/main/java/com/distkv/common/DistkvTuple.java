package com.distkv.common;

public class DistkvTuple<X, Y> {

  private X firstElement;

  private Y secondElement;

  public DistkvTuple(X firstElement, Y secondElement) {
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
