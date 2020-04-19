package com.distkv.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtil {
  public static String getLocalIp() {
    try {
      InetAddress inetAddress = InetAddress.getLocalHost();
      String ip = inetAddress.getHostAddress().toString();
      return ip;
    } catch (UnknownHostException e) {
      return "127.0.0.1";
    }
  }
}
