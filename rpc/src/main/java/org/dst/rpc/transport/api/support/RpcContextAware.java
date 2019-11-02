package org.dst.rpc.transport.api.support;

/**
 * 如果业务方希望自己去介入Rpc的一些过程的话，业务接口可以继承该接口，Rpc框架会自动探测该接口，并注入合适的RpcContext
 *
 * @author zrj CreateDate: 2019/11/2
 */
public interface RpcContextAware {

  void setRpcContext(RpcContext rpcContext);

}
