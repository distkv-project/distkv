package org.dst.rpc.core.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.dst.rpc.core.common.constants.GlobalConstants;
import org.dst.rpc.core.exception.DstException;
import org.dst.rpc.core.model.IpPortPair;

/**
 * 语言透明的服务描述，配置总线。
 */
public class URL {

  /**
   * 协议
   */
  private String protocol;

  /**
   * host地址
   */
  private String host;

  /**
   * 服务端口
   */
  private int port;

  /**
   * 任务路径，等同于interfaceName
   */
  private String path;

  /**
   * 通用设置
   */
  private Map<String, String> parameters;


  /* constructor */
  public URL() {
    parameters = new ConcurrentHashMap<>();
  }

  public URL(String protocol, String address) {
    this.protocol = protocol;
    getHostPostFromAddress(address);
    parameters = new ConcurrentHashMap<>();
  }

  public URL(String protocol, String address, String path) {
    this.protocol = protocol;
    getHostPostFromAddress(address);
    this.path = path;
    parameters = new ConcurrentHashMap<>();
  }

  public URL(String protocol, String host, int port, String path) {
    this.protocol = protocol;
    this.host = host;
    this.port = port;
    this.path = path;
    parameters = new ConcurrentHashMap<>();
  }

  public URL(String protocol, String host, int port, String path,
      Map<String, String> parameters) {
    this.protocol = protocol;
    this.host = host;
    this.port = port;
    this.path = path;
    this.parameters = parameters;
  }

  private void getHostPostFromAddress(String address) {
    setAddress(address);
  }

  public void setAddress(String address) {
    if (address == null || address.length() == 0 || !address.contains(":")) {
      throw new IllegalArgumentException("URL: address error: " + address);
    }
    String[] split = address.split(":");
    if (split.length < 2) {
      throw new IllegalArgumentException("URL: address error: " + address);
    }
    this.host = split[0];
    try {
      this.port = Integer.valueOf(split[1]);
    } catch (Exception e) {
      throw new IllegalArgumentException("URL: address error: " + address, e);
    }
  }
  /* ------ */

  /* static method */

  public static String encode(String url) {
    if (url == null || "".equals(url)) {
      return "";
    }
    try {
      return URLEncoder.encode(url, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static String decode(String url) {
    if (url == null || "".equals(url)) {
      return "";
    }
    try {
      return URLDecoder.decode(url, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * copy from motan, xixi
   */
  public static URL parse(String url) {
    if (url == null || url.length() == 0) {
      throw new DstException("URL: url is null");
    }
    String protocol = null;
    String host = null;
    int port = 0;
    String path = null;
    Map<String, String> parameters = new HashMap<>();
    int i = url.indexOf("?"); // seperator between body and parameters
    if (i >= 0) {
      String[] parts = url.substring(i + 1).split("\\&");

      for (String part : parts) {
        part = part.trim();
        if (part.length() > 0) {
          int j = part.indexOf('=');
          if (j >= 0) {
            parameters.put(part.substring(0, j), part.substring(j + 1));
          } else {
            parameters.put(part, part);
          }
        }
      }
      url = url.substring(0, i);
    }
    i = url.indexOf(GlobalConstants.PROTOCOL_SEP);
    if (i >= 0) {
      if (i == 0) {
        throw new IllegalStateException("url missing protocol: \"" + url + "\"");
      }
      protocol = url.substring(0, i);
      url = url.substring(i + GlobalConstants.PROTOCOL_SEP.length());
    } else {
      i = url.indexOf(":/");
      if (i >= 0) {
        if (i == 0) {
          throw new IllegalStateException("url missing protocol: \"" + url + "\"");
        }
        protocol = url.substring(0, i);
        url = url.substring(i + 1);
      }
    }

    i = url.indexOf("/");
    if (i >= 0) {
      path = url.substring(i + 1);
      url = url.substring(0, i);
    }

    i = url.indexOf(":");
    if (i >= 0 && i < url.length() - 1) {
      port = Integer.parseInt(url.substring(i + 1));
      url = url.substring(0, i);
    }
    if (url.length() > 0) {
      host = url;
    }
    return new URL(protocol, host, port, path, parameters);
  }

  public static String buildHostPortStr(String host, int defaultPort) {
    if (defaultPort <= 0) {
      return host;
    }

    int idx = host.indexOf(":");
    if (idx < 0) {
      return host + ":" + defaultPort;
    }

    int port = Integer.parseInt(host.substring(idx + 1));
    if (port <= 0) {
      return host.substring(0, idx + 1) + defaultPort;
    }
    return host;
  }

  public IpPortPair getIpPortPair() {
    return new IpPortPair(host, port);
  }

  public String getIpPortString() {
    return buildHostPortStr(host, port);
  }

  public String getUri() {
    return protocol + GlobalConstants.PROTOCOL_SEP + host + ":" + port +
        GlobalConstants.PATH_SEP + path;
  }

  public String getUrlString() {
    StringBuilder builder = new StringBuilder();
    builder.append(getUri()).append("?");

    for (Map.Entry<String, String> entry : parameters.entrySet()) {
      String name = entry.getKey();
      String value = entry.getValue();
      builder.append(name).append("=").append(value).append("&");
    }

    return builder.toString();
  }


  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public Map<String, String> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, String> parameters) {
    this.parameters = parameters;
  }



  /* -- 获取配置方法 -- */

  public boolean getBoolean(String key, boolean def) {
    if (parameters.containsKey(key)) {
      return Boolean.valueOf(parameters.get(key));
    }
    return def;
  }

  public short getShort(String key, short def) {
    if (parameters.containsKey(key)) {
      return Short.valueOf(parameters.get(key));
    }
    return def;
  }

  public int getInt(String key, int def) {
    if (parameters.containsKey(key)) {
      return Short.valueOf(parameters.get(key));
    }
    return def;
  }

  public long getLong(String key, long def) {
    if (parameters.containsKey(key)) {
      return Long.valueOf(parameters.get(key));
    }
    return def;
  }

  public String getString(String key, String def) {
    if (parameters.containsKey(key)) {
      return parameters.get(key);
    }
    return def;
  }

  public double getDouble(String key, double def) {
    if (parameters.containsKey(key)) {
      return Double.valueOf(parameters.get(key));
    }
    return def;
  }

  public void setConfig(String key, String value) {
    parameters.put(key, value);
  }


  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof URL)) {
      return false;
    }
    try {
      URL o = (URL) obj;
      if (!Objects.equals(o.protocol, protocol)) {
        return false;
      }
      if (!Objects.equals(o.host, host)) {
        return false;
      }
      if (!Objects.equals(o.port, port)) {
        return false;
      }
      if (!Objects.equals(o.path, path)) {
        return false;
      }
      if (!Objects.equals(o.parameters, parameters)) {
        return false;
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public int hashCode() {
    int factor = 31;
    int rs = 1;
    rs = factor * rs + Objects.hashCode(protocol);
    rs = factor * rs + Objects.hashCode(host);
    rs = factor * rs + Objects.hashCode(port);
    rs = factor * rs + Objects.hashCode(path);
    rs = factor * rs + Objects.hashCode(parameters);
    return rs;
  }
}
