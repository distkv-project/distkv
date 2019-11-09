package org.dst.client;

import org.dst.common.exception.DictKeyNotFoundException;
import org.dst.common.exception.DstException;
import org.dst.common.exception.KeyNotFoundException;
import org.dst.rpc.protobuf.generated.CommonProtocol;
import org.dst.rpc.protobuf.generated.StringProtocol;
import org.dst.rpc.service.DstStringService;

public class DstStringProxy {

  private DstStringService service;

  public DstStringProxy(DstStringService service) {
    this.service = service;
  }

  public void put(String key, String value) {
    StringProtocol.PutRequest request =
        StringProtocol.PutRequest.newBuilder()
            .setKey(key)
            .setValue(value)
            .build();

    StringProtocol.PutResponse response = service.put(request);
    if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public String get(String key) throws DstException {
    StringProtocol.GetRequest request =
        StringProtocol.GetRequest.newBuilder()
            .setKey(key)
            .build();

    StringProtocol.GetResponse response = service.get(request);
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }

    return response.getValue();
  }

  public void del(String key){
    CommonProtocol.DropRequest.Builder request = CommonProtocol.DropRequest.newBuilder();
    request.setKey(key);
    CommonProtocol.DropResponse response = service.drop(request.build());
    switch(response.getStatus()){
      case OK:
        break;
      case KEY_NOT_FOUND:
        throw new KeyNotFoundException(key);
      case UNKNOWN_ERROR:
        break;
      case DICT_KEY_NOT_FOUND:
        throw new DictKeyNotFoundException(key);
        default:
          throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }
}
