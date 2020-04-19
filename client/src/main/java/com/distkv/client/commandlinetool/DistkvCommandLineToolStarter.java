package com.distkv.client.commandlinetool;

import com.distkv.common.exception.DictKeyNotFoundException;
import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvListIndexOutOfBoundsException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.exception.SlistMemberNotFoundException;
import com.distkv.common.exception.SlistTopNumIsNonNegativeException;
import com.distkv.parser.DistkvParser;
import com.distkv.parser.po.DistkvParsedResult;
import java.io.IOException;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.distkv.client.DefaultDistkvClient;
import com.distkv.client.DistkvClient;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.NullCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class DistkvCommandLineToolStarter {

  private static final String PROGRAM_NAME = "Distkv";

  private static final String PROMPT_STRING = "dkv-cli> ";

  private static final String DEFAULT_VERSION = "0.1.0";

  @Parameter(names = {"--help", "-help"}, description = "Show help messages.",
      help = true, order = -1)
  private static boolean HELP = false;

  @Parameter(names = {"--address"}, description = "Specify the address of server to connect.",
      order = 1)
  private static String ADDRESS = "127.0.0.1:8082";

  @Parameter(names = {"-v", "-version", "-V"}, description = "Show the version of Distkv.",
      help = true, order = 2)
  private static boolean VERSION = false;


  public static void main(String[] args) {

    DistkvCommandLineToolStarter distkvCommandLineToolStarter = new DistkvCommandLineToolStarter();
    JCommander jcommander = JCommander.newBuilder().addObject(
        distkvCommandLineToolStarter).build();
    jcommander.setProgramName(PROGRAM_NAME);

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

    DistkvClient distkvClient = null;
    try {
      distkvClient = new DefaultDistkvClient(String.format("distkv://%s", ADDRESS));
    } catch (Exception e) {
      System.out.println(String.format("Failed to connect to distkv server, %s, "
          + "please check your input.", ADDRESS));
      return;
    }
    try  {
      new DistkvCommandLineToolStarter().loop(distkvClient);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loop(DistkvClient distkvClient) throws IOException {
    DistkvParser distkvParser = new DistkvParser();
    DistkvCommandExecutor distkvCommandExecutor = new DistkvCommandExecutor(distkvClient);
    Completer strPutCompleter = new ArgumentCompleter(
        new StringsCompleter("str.put"),
        new StringsCompleter("*"),
        new StringsCompleter("*"),
        NullCompleter.INSTANCE);
    Completer strGetCompleter = new ArgumentCompleter(
        new StringsCompleter("str.get"),
        new StringsCompleter("*"),
        new StringsCompleter("*"),
        NullCompleter.INSTANCE);
    Completer allCompleters = new AggregateCompleter(strPutCompleter, strGetCompleter);

    Terminal terminal = TerminalBuilder.builder().system(true).build();
    LineReader lineReader = LineReaderBuilder.builder().terminal(terminal)
                            .completer(allCompleters).build();
    final String prompt = "dkv-cli >";
    while (true) {
      String result = null;
      String line;
      line = lineReader.readLine(prompt);
      try {
        DistkvParsedResult parsedResult = distkvParser.parse(line);
        result = distkvCommandExecutor.execute(parsedResult);
      } catch (DictKeyNotFoundException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      } catch (DistkvListIndexOutOfBoundsException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      } catch (KeyNotFoundException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      } catch (SlistMemberNotFoundException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      } catch (SlistTopNumIsNonNegativeException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      } catch (DistkvException e) {
        result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
      }
      System.out.println(PROMPT_STRING + result);

    }
  }

}
