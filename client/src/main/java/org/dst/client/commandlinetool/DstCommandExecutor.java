package org.dst.client.commandlinetool;

import org.dst.client.DstClient;
import org.dst.parser.po.DstParsedResult;
import org.dst.parser.po.RequestTypeEnum;
import org.dst.rpc.protobuf.generated.ListProtocol;
import org.dst.rpc.protobuf.generated.StringProtocol;
import java.util.List;

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
      ListProtocol.PutRequest request =
          (ListProtocol.PutRequest) parsedResult.getRequest();
      dstClient.lists().put(request.getKey(), request.getValuesList());
      return STATUS_OK;
    } else if (parsedResult.getRequestType() == RequestTypeEnum.LIST_LPUT) {
      ListProtocol.PutRequest request =
          (ListProtocol.PutRequest) parsedResult.getRequest();
      dstClient.lists().lput(request.getKey(), request.getValuesList());
      return STATUS_OK;
    } else if (parsedResult.getRequestType() == RequestTypeEnum.LIST_RPUT) {
      ListProtocol.PutRequest request =
          (ListProtocol.PutRequest) parsedResult.getRequest();
      dstClient.lists().rput(request.getKey(), request.getValuesList());
      return STATUS_OK;
    } else if (parsedResult.getRequestType() == RequestTypeEnum.LIST_GET) {
      ListProtocol.GetRequest request =
              (ListProtocol.GetRequest) parsedResult.getRequest();
      List<String> list = null;
      if (request.getType() == ListProtocol.GetType.GET_ALL) {
        list = dstClient.lists().get(request.getKey());
      } else if (request.getType() == ListProtocol.GetType.GET_ONE) {
        list = dstClient.lists().get(request.getKey(), request.getIndex());
      } else if (request.getType() == ListProtocol.GetType.GET_RANGE) {
        list = dstClient.lists().get(request.getKey(), request.getFrom(), request.getEnd());
      }
      String result = list.toString();
      return result;
    } else if (parsedResult.getRequestType() == RequestTypeEnum.LIST_REMOVE) {
      ListProtocol.RemoveRequest request =
              (ListProtocol.RemoveRequest) parsedResult.getRequest();
      if (request.getType() == ListProtocol.RemoveType.RemoveOne) {
        dstClient.lists().remove(request.getKey(), request.getIndex());
        return STATUS_OK;
      } else if (request.getType() == ListProtocol.RemoveType.RemoveRange) {
        dstClient.lists().remove(request.getKey(), request.getFrom(), request.getEnd());
        return STATUS_OK;
      }
    } else if (parsedResult.getRequestType() == RequestTypeEnum.LIST_M_REMOVE) {
      ListProtocol.MRemoveRequest request =
              (ListProtocol.MRemoveRequest) parsedResult.getRequest();
      dstClient.lists().multipleRemove(request.getKey(), request.getIndexesList());
      return STATUS_OK;
    }
    return null;
  }
}
