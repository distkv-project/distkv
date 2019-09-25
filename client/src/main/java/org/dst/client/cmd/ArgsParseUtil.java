package org.dst.client.cmd;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.dst.common.exception.DstException;

public class ArgsParseUtil {

  private static final String DEFAULT_VERSION = "0.1.0";

  @Parameter(names = {"--help", "-help"}, description = "show the help command",
          help = true, order = 0)
  private static boolean HELP = false;
  @Parameter(names = {"-h", "-H"}, description = "specify the host of server to connect", order = 1)
  private static String HOST = "127.0.0.1";
  @Parameter(names = {"-p", "-P"}, description = "specify the port of server to connect", order = 2)
  private static String PORT = "8082";
  @Parameter(names = {"-v", "-version", "-V"}, description = "show the version of Dst",
      help = true, order = 3)
  private static boolean VERSION = false;

  /**
   * @param args the args like [-h,127.0.0.1,-p,8082] or [-h,127.0.0.1] or [-p,8082]
   * @return list://127.0.0.1:8082
   */
  public static String covertArgsToAddress(String[] args) throws DstException {

    StringBuilder sb = new StringBuilder();

    ArgsParseUtil argsParseUtil = new ArgsParseUtil();
    JCommander jcommander = JCommander.newBuilder().addObject(argsParseUtil).build();
    try {
      jcommander.parse(args);
    } catch (Exception e) {
      jcommander.setProgramName("dst");
      jcommander.usage(sb);
      throw new DstException(sb.toString());
    }

    if (HELP) {
      jcommander.setProgramName("dst");
      jcommander.usage(sb);
      throw new DstException(sb.toString());
    }

    if (VERSION) {
      throw new DstException(DEFAULT_VERSION);
    }

    sb.append("list://");
    sb.append(HOST);
    sb.append(":");
    sb.append(PORT);
    return sb.toString();
  }

}
