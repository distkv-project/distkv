package com.distkv.server.metaserver.server.bean;

import com.distkv.server.view.NodeTable;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class GetGlobalViewResponse extends BaseResponse implements Serializable {

  private static final long serialVersionUID = -7220117786727166673L;

  private ConcurrentHashMap<String, NodeTable> globalView;


  public ConcurrentHashMap<String, NodeTable> getGlobalView() {
    return globalView;
  }

  public void setGlobalView(ConcurrentHashMap<String, NodeTable> globalView) {
    this.globalView = globalView;
  }
}
