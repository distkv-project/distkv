package com.distkv.dst.client.commandlinetool;

import java.util.Scanner;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.distkv.dst.client.DefaultDstClient;
import com.distkv.dst.client.DstClient;
import com.distkv.dst.parser.DstParser;
import com.distkv.dst.parser.po.DstParsedResult;

public class DstCommandLineToolStarter {

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


  public static void main(String[] args) {

    DstCommandLineToolStarter dstCommandLineToolStarter = new DstCommandLineToolStarter();
    JCommander jCommander = JCommander.newBuilder().addObject(
        dstCommandLineToolStarter).build();
    jCommander.setProgramName("dst");

    try {
      jCommander.parse(args);
    } catch (ParameterException e) {
      jCommander.usage();
      return;
    }

    if (HELP) {
      jCommander.usage();
      return;
    }

    if (VERSION) {
      System.out.println(DEFAULT_VERSION);
      return;
    }

    DstClient dstClient = null;
    try {
      dstClient = new DefaultDstClient(String.format("dst://%s", ADDRESS));
    } catch (Exception e) {
      System.out.println(String.format("Failed to connect to dst server, %s, "
              + "please check your input.", ADDRESS));
      return;
    }
    new DstCommandLineToolStarter().loop(dstClient);
  }

  private void loop(DstClient dstClient) {
    DstParser dstParser = new DstParser();
    DstCommandExecutor dstCommandExecutor = new DstCommandExecutor(dstClient);
    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.print("dst-cli> ");
      final String command = sc.nextLine();
      DstParsedResult parsedResult = dstParser.parse(command);
      String result = dstCommandExecutor.execute(parsedResult);
      System.out.println("dst-cli> " + result);
    }
  }

}
