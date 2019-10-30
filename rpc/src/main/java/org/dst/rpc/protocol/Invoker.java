package org.dst.rpc.protocol;

import org.dst.rpc.core.common.URL;
import org.dst.rpc.transport.api.async.Request;
import org.dst.rpc.transport.api.async.Response;

/**
 * @author zrj CreateDate: 2019/10/28
 */
public interface Invoker<T> {

  URL getURL();

  Class<T> getInterface();

  Response invoke(Request request);

}
