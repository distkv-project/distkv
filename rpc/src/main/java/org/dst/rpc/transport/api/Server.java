package org.dst.rpc.transport.api;

import java.util.concurrent.Executor;
import org.dst.rpc.core.codec.Codec;
import org.dst.rpc.core.common.URL;

/**
 *
 */
public interface Server {

  URL getUrl();

  void open();

  boolean isOpen();

  void close();

  Codec getCodec();

  RoutableHandler getRoutableHandler();

  Executor getExecutor();

}
