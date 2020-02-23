package dashboard;

import com.distkv.client.DefaultDistkvClient;
import com.distkv.client.commandlinetool.DistkvCommandExecutor;
import com.distkv.common.exception.*;
import com.distkv.parser.DistkvParser;
import com.distkv.parser.po.DistkvParsedResult;
import fi.iki.elonen.NanoHTTPD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class DashBoardServer extends NanoHTTPD {

  DistkvCommandExecutor distkvCommandExecutor;

  private DistkvParser distkvParser = new DistkvParser();

  public DashBoardServer() throws IOException {
    super(12223);

    // Init distkv related component.
    distkvCommandExecutor = new DistkvCommandExecutor(new DefaultDistkvClient("distkv://127.0.0.1:8082"));

    start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    System.out.println("\nRunning! Point your browsers to http://localhost:12223/ \n");
  }

//  @Override
//  public Response serve(IHTTPSession session) {
//    String msg = "<html><body><h1>Hello server</h1>\n";
//    Map<String, String> parms = session.getParms();
//
//    if (parms.get("username") == null) {
//      msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
//    } else {
//      msg += "<p>Hello, " + parms.get("username") + "!</p>";
//    }
//    return newFixedLengthResponse(msg + "</body></html>\n");
//  }

  @Override
  public Response serve(IHTTPSession session) {
    if ("/".equals(session.getUri())) {
      File file = new File("/Users/wangqing/Workspace/distkv/tool/src/main/java/dashboard/index.html");
      return render200("/index.html", file);
    }

    if (!"/run_cli".equals(session.getUri())) {
      return newFixedLengthResponse("unknown uri:" + session.getUri());
    }
    // This is the code path of `/run_cli` URI.
    try {
      session.parseBody(null);
    } catch (IOException | ResponseException e) {
      // TODO(qwang): Refine this with exception log.
      System.exit(1);
    }

    // This is the code path of uri `/run_cli`
    Map<String, String> params = session.getParms();
    final String command = params.get("text");
    String result = null;
    try {
      DistkvParsedResult parsedResult = distkvParser.parse(command);
      result = distkvCommandExecutor.execute(parsedResult);
    } catch (DictKeyNotFoundException e) {
      result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
    } catch (DistkvListIndexOutOfBoundsException e) {
      result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
    } catch (KeyNotFoundException e) {
      result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
    } catch (SortedListMemberNotFoundException e) {
      result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
    } catch (SortedListTopNumIsNonNegativeException e) {
      result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
    } catch (DistkvException e) {
      result = ("errorCode: " + e.getErrorCode() + ";\n Detail: " + e.getMessage());
    }


    return newFixedLengthResponse(result);
  }

  private Response render200(String uri, File file) {
    try {
      return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, NanoHTTPD.getMimeTypeForFile(uri), new FileInputStream(file), file.length());
    } catch (FileNotFoundException e) {
//      return render500(e.getMessage());
      // TODO(qwang):
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    try {
      new DashBoardServer();
    } catch (IOException ioe) {
      System.err.println("Couldn't start server:\n" + ioe);
    }
  }

}
