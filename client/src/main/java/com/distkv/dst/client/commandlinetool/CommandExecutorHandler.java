package com.distkv.dst.client.commandlinetool;

import com.distkv.dst.client.DstClient;
import com.distkv.dst.parser.po.DstParsedResult;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;

public class CommandExecutorHandler {
  public static void strPut(DstClient dstclient, DstParsedResult parsedResult) {
    StringProtocol.PutRequest putRequestStr =
        (StringProtocol.PutRequest) parsedResult.getRequest();
    dstclient.strs().put(putRequestStr.getKey(), putRequestStr.getValue());
  }
}
