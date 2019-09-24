package org.dst.client.cmd;

import org.dst.common.exception.DstException;

public class ArgsParseUtil {
  private static final String defaultHost = "127.0.0.1";
  private static final String defaultPort = "8082";

  /**
   * @param args the args like [-h,127.0.0.1,-p,8082] or [-h,127.0.0.1] or [-p,8082]
   * @return list://127.0.0.1:8082
   */
  public static String covertArgsToAddress(String[] args) throws DstException {
    String address;
    if (args.length == 2) {
      //-p 8082 or -h 127.0.0.1
      if ("-h".equals(args[0])) {
        address = covertArgsToAddress(args[1], defaultPort);
      } else if ("-p".equals(args[0])) {
        address = covertArgsToAddress(defaultHost, args[1]);
      } else {
        throw new DstException("the args are wrong. the example input is \"-h 127.0.0.1 -p 8082\"");
      }
    } else if (args.length == 4) {
      //-h 127.0.0.1 -p 8082 or -p 8082 -h 127.0.0.1
      if ("-h".equals(args[0]) && "-p".equals(args[2])) {
        address = covertArgsToAddress(args[1], args[3]);
      } else if ("-p".equals((args[0])) && "-h".equals((args[2]))) {
        address = covertArgsToAddress(args[3], args[1]);
      } else {
        throw new DstException("the args are wrong. the example input is \"-h 127.0.0.1 -p 8082\"");
      }
    } else {
      throw new DstException("the args are wrong. the example input is \"-h 127.0.0.1 -p 8082\"");
    }

    return address;
  }

  private static String covertArgsToAddress(String host, String port) {
    StringBuilder sb = new StringBuilder();
    sb.append("list://");
    sb.append(host);
    sb.append(":");
    sb.append(port);
    return sb.toString();
  }

}
