package org.dst.client.commandlinetool;

import org.dst.client.DstClient;
import org.dst.parser.po.DstParsedResult;
import org.dst.parser.po.RequestTypeEnum;
import org.dst.rpc.protobuf.generated.StringProtocol;

public class DstCommandExecutor {

  private static final String STATUS_OK = "ok";

  DstClient dstClient;

  public DstCommandExecutor(DstClient dstClient) {
    this.dstClient = dstClient;
  }

  public String execute(DstParsedResult parsedResult) {
    if (parsedResult.getRequestType() == RequestTypeEnum.STR_PUT) {
      StringProtocol.PutRequest request
          = (StringProtocol.PutRequest) parsedResult.getRequest();
      dstClient.strs().put(request.getKey(), request.getValue());
      return STATUS_OK;
    } else if (parsedResult.getRequestType() == RequestTypeEnum.STR_GET) {
      StringProtocol.GetRequest request =
          (StringProtocol.GetRequest) parsedResult.getRequest();
      return dstClient.strs().get(request.getKey());
    } else if (parsedResult.getRequestType() == RequestTypeEnum.LIST_PUT) {
      StringProtocol.GetRequest request =
          (StringProtocol.GetRequest) parsedResult.getRequest();
      // TODO(qwang): TODO
    }
    return null;
  }
}
