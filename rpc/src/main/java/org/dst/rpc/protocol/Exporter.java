package org.dst.rpc.protocol;


import org.dst.rpc.core.common.URL;
import org.dst.rpc.core.utils.NetUtils;
import org.dst.rpc.transport.api.Handler;
import org.dst.rpc.transport.api.Server;
import org.dst.rpc.transport.netty.NettyTransportFactory;

/**
 * @author zrj CreateDate: 2019/10/28
 */
public class Exporter<T> {

  private T ref;
  private Class<T> interfaceClass;
  private int port;
  private URL serverUrl;
  private String protocol;

  public Exporter() {
    serverUrl = new URL();
    String localAddress = NetUtils.getLocalAddress().getHostAddress();
    serverUrl.setHost(localAddress);
  }

  public void setRef(T ref) {
    this.ref = ref;
  }

  public void isLocal(boolean isLocal) {
    if (isLocal) {
      serverUrl.setHost("127.0.0.1");
    }
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
    serverUrl.setProtocol(protocol);
  }

  public void setInterfaceClass(Class<T> interfaceClass) {
    this.interfaceClass = interfaceClass;
    serverUrl.setPath(interfaceClass.getName());
  }

  public void setPort(int port) {
    this.port = port;
    serverUrl.setPort(port);
  }


  public void export() {
    Handler handler = new HandlerDelegate(new ServerImpl<>(serverUrl, ref, interfaceClass));
    Server server = NettyTransportFactory.getInstance().createServer(serverUrl, handler);
    server.open();
  }
}
