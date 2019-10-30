package org.dst.rpc.protocol;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.dst.rpc.core.common.URL;
import org.dst.rpc.core.exception.DstException;
import org.dst.rpc.core.utils.ReflectUtils;
import org.dst.rpc.transport.api.async.DefaultResponse;
import org.dst.rpc.transport.api.async.Request;
import org.dst.rpc.transport.api.async.Response;

/**
 * @author zrj CreateDate: 2019/10/28
 */
public class ServerImpl<T> implements Invoker<T> {

  protected Map<String, Method> methodMap = new ConcurrentHashMap<>();

  private T ref;

  private Class<T> interfaceClazz;

  private URL serverUrl;

  /**
   * 找到所有interfaceClazz可以调用的方法，并缓存下来，缓存名字要保留参数类型的完整名称，防止函数重载
   */
  public ServerImpl(URL serverUrl, T ref, Class<T> interfaceClazz) {
    if (!interfaceClazz.isInterface()) {
      throw new DstException("ServerImpl: interfaceClazz is not a interface!");
    }
    this.serverUrl = serverUrl;
    this.ref = ref;
    this.interfaceClazz = interfaceClazz;
    List<Method> methods = ReflectUtils.parseMethod(interfaceClazz);
    for (Method method : methods) {
      String methodDesc = ReflectUtils.getMethodDesc(method);
      methodMap.putIfAbsent(methodDesc, method);
    }
  }

  @Override
  public Class<T> getInterface() {
    return interfaceClazz;
  }

  @Override
  public URL getURL() {
    return serverUrl;
  }

  @Override
  public Response invoke(Request request) {
    Response response = new DefaultResponse();
    response.setRequestId(request.getRequestId());
    String methodName = ReflectUtils.getMethodDesc(request.getMethodName(), request.getArgsType());
    Method method = methodMap.get(methodName);
    if (method == null) {
      response.setException(new DstException("ServerImpl: can't find method: " + methodName));
      return response;
    }
    try {
      Object value = method.invoke(ref, request.getArgsValue());
      if (value instanceof Response) {
        Response innerResponse = (Response) value;
        response.setValue(innerResponse.getValue());
        response.setException(innerResponse.getException());
      } else {
        response.setValue(value);
      }
    } catch (Exception e) {
      response.setException(
          new DstException("ServerImpl: dst.exception when invoke method: " + methodName, e));
    } catch (Error e) {
      response
          .setException(new DstException("ServerImpl: error when invoke method: " + methodName, e));
    }
    return response;
  }

}
