package org.dst.rpc.transport.api;

import org.dst.rpc.transport.api.async.Request;
import org.dst.rpc.transport.api.async.Response;

/**
 * 客户端，客户端具有请求的功能
 */
public interface Client extends Endpoint {

  boolean isAsync();

  Response invoke(Request request);

}
