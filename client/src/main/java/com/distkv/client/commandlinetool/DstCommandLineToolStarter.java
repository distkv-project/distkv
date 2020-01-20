package com.distkv.client.commandlinetool;

import java.util.Scanner;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.distkv.client.DefaultDstClient;
import com.distkv.client.DstClient;
import com.distkv.common.exception.DistKVException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.exception.DictKeyNotFoundException;
import com.distkv.common.exception.DistKVListIndexOutOfBoundsException;
import com.distkv.common.exception.SortedListMemberNotFoundException;
import com.distkv.common.exception.SortedListTopNumIsNonNegativeException;
import com.distkv.parser.DistKVParser;
import com.distkv.parser.po.DistKVParsedResult;

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
    jcommander.setProgramName("distkv");

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
      dstClient = new DefaultDstClient(String.format("distkv://%s", ADDRESS));
    } catch (Exception e) {
      System.out.println(String.format("Failed to connect to dst server, %s, "
              + "please check your input.", ADDRESS));
      return;
    }
    new DstCommandLineToolStarter().loop(dstClient);
  }

  private void loop(DstClient dstClient) {
    DistKVParser dstParser = new DistKVParser();
    DstCommandExecutor dstCommandExecutor = new DstCommandExecutor(dstClient);
    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.print("distkv-cli> ");
      final String command = sc.nextLine();
      String result = null;
      try {
        DistKVParsedResult parsedResult = dstParser.parse(command);
        result = dstCommandExecutor.execute(parsedResult);
      } catch (DictKeyNotFoundException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      } catch (DistKVListIndexOutOfBoundsException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      } catch (KeyNotFoundException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      } catch (SortedListMemberNotFoundException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      } catch (SortedListTopNumIsNonNegativeException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      } catch (DistKVException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      }
      System.out.println("distkv-cli> " + result);
    }
  }

}
