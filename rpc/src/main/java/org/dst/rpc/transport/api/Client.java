package org.dst.rpc.transport.api;

import org.dst.rpc.core.codec.Codec;
import org.dst.rpc.core.common.URL;
import org.dst.rpc.transport.api.async.Request;
import org.dst.rpc.transport.api.async.Response;

/**
 * 客户端，客户端具有请求的功能
 */
public interface Client {

  URL getUrl();

  void open();

  boolean isOpen();

  void close();

  Codec getCodec();

  Response invoke(Request request);

}
