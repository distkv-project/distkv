package com.distkv.dst.client.commandlinetool;

import java.util.Scanner;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.distkv.dst.client.DefaultDstClient;
import com.distkv.dst.client.DstClient;
import com.distkv.dst.common.exception.DstException;
import com.distkv.dst.common.exception.KeyNotFoundException;
import com.distkv.dst.common.exception.DictKeyNotFoundException;
import com.distkv.dst.common.exception.DstListIndexOutOfBoundsException;
import com.distkv.dst.common.exception.SortedListMemberNotFoundException;
import com.distkv.dst.common.exception.SortedListTopNumBeNonNegativeException;
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
    JCommander jcommander = JCommander.newBuilder().addObject(
        dstCommandLineToolStarter).build();
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
      String result = null;
      try {
        DstParsedResult parsedResult = dstParser.parse(command);
        result = dstCommandExecutor.execute(parsedResult);
      } catch (DictKeyNotFoundException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      } catch (DstListIndexOutOfBoundsException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      } catch (KeyNotFoundException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      } catch (SortedListMemberNotFoundException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      } catch (SortedListTopNumBeNonNegativeException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      } catch (DstException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      }
      System.out.println("dst-cli> " + result);
    }
  }

}
