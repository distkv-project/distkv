package org.dst.client.cmd;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import org.dst.client.DefaultDstClient;

public class DstCmdStarter {

  private static final String DEFAULT_VERSION = "0.1.0";

  @Parameter(names = {"--help", "-help"}, description = "Show help messages.",
          help = true, order = -1)
  private static boolean HELP = false;

  @Parameter(names = {"--address"}, description = "Specify the address of server to connect.",
          order = 1)
  private static String ADDRESS = "127.0.0.1:8082";

  @Parameter(names = {"-v", "-version", "-V"}, description = "Show the version of Dst.",
          help = true, order = 2)
  private static boolean VERSION = false;

  private static DefaultDstClient client;

  private static HashMap<DstOperationType, Function<DstCommandWithType, ClientResult>>
          commandHandlers = new HashMap<>();

  public static void main(String[] args) {

    DstCmdStarter dstCmdStarter = new DstCmdStarter();
    JCommander jcommander = JCommander.newBuilder().addObject(dstCmdStarter).build();
    jcommander.setProgramName("dst");

    try {
      jcommander.parse(args);
    } catch (ParameterException e) {
      jcommander.usage();
      return;
    }

    if (HELP) {
      jcommander.usage();
      return;
    }

    if (VERSION) {
      System.out.println(DEFAULT_VERSION);
      return;
    }

    try {
      client = new DefaultDstClient(String.format("list://%s", ADDRESS));
    } catch (Exception e) {
      System.out.println("connect failure, please check your input.");
      return;
    }

    //register different operation type handler
    commandHandlers.put(DstOperationType.STRING, new StringHandler(client));
    commandHandlers.put(DstOperationType.SET, new SetHandler(client));
    commandHandlers.put(DstOperationType.LIST, new ListHandler(client));
    commandHandlers.put(DstOperationType.DICT, new DictHandler(client));
    commandHandlers.put(DstOperationType.TABLE, new TableHandler(client));
    commandHandlers.put(DstOperationType.UNKNOWN, new UnkonwnHandler(client));
    new DstCmdStarter().loop();
  }

  private void loop() {
    Parser parser = new Parser();
    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.print("dst-cli> ");
      String line = sc.nextLine();
      DstCommandWithType commandWithType = parser.parse(line);
      ClientResult clientResult = executeCommand(commandWithType);
      System.out.println("dst-cli> " + clientResult);
    }
  }

  private ClientResult executeCommand(DstCommandWithType commandWithType) {
    return commandHandlers.get(commandWithType.operationType).apply(commandWithType);
  }
}
