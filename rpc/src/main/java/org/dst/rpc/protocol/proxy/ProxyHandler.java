package org.dst.rpc.protocol.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.dst.rpc.core.common.URL;
import org.dst.rpc.core.exception.DstException;
import org.dst.rpc.core.utils.RequestIdGenerator;
import org.dst.rpc.protocol.Invoker;
import org.dst.rpc.transport.api.async.AsyncResponse;
import org.dst.rpc.transport.api.async.Request;
import org.dst.rpc.transport.api.async.Response;


public class ProxyHandler<T> implements InvocationHandler {

  private URL url;

  private Invoker invoker;

  private Class<T> interfaceClazz;

  public ProxyHandler(Class<T> clazz, URL url, Invoker invoker) {
    interfaceClazz = clazz;
    this.url = url;
    this.invoker = invoker;
  }

  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    if (isLocalMethod(method)) {
      if ("toString".equals(method.getName())) {
        return "";
      }
      if ("equals".equals(method.getName())) {
        return false;
      }
      throw new DstException("can not invoke local method:" + method.getName());
    }

    Request request = new Request();
    request.setRequestId(RequestIdGenerator.next());
    request.setInterfaceName(method.getDeclaringClass().getName());
    request.setMethodName(method.getName());
    request.setArgsType(getArgsTypeString(method));
    request.setArgsValue(args);

    Response response = invoker.invoke(request);
    /*
       fixme 这里为了让业务方使用异步，这里将Response返回给调用方了。
       这里我觉得更好的方法是返回一个JDK的Future，比较通用，不然rpc框架对业务方有侵入了。
     */
    if (response instanceof AsyncResponse) {
      if (Response.class.isAssignableFrom(method.getReturnType())) {
        // fixme 这里不要直接返回Response，应该返回一个门面，不然的话没有将框架内的东西封装住。
        return response;
      } else {
        throw new DstException("Method return type must extend from AsyncResponse");
      }
    }

    // 如果不是异步的，直接同步返回结果。
    if (response.getException() != null) {
      throw response.getException();
    }

    return response.getValue();
  }

  private String getArgsTypeString(Method method) {
    Class<?>[] pts = method.getParameterTypes();
    if (pts.length <= 0) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (Class clazz : pts) {
      sb.append(clazz.getName()).append(",");
    }
    if (sb.length() > 0) {
      sb.setLength(sb.length() - ",".length());
    }
    return sb.toString();
  }

  /**
   * tostring,equals,hashCode,finalize等接口未声明的方法不进行远程调用
   */
  public boolean isLocalMethod(Method method) {
    if (method.getDeclaringClass().equals(Object.class)) {
      try {
        Method interfaceMethod = interfaceClazz
            .getDeclaredMethod(method.getName(), method.getParameterTypes());
        return false;
      } catch (Exception e) {
        return true;
      }
    }
    return false;
  }
}
