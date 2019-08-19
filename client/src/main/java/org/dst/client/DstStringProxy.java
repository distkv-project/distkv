package org.dst.client;

import org.dst.exception.DstException;
import org.dst.exception.KeyNotFoundException;
import org.dst.protocol.StatusProtocol;
import org.dst.protocol.StringProtocol;
import org.dst.server.service.DstStringService;

public class DstStringProxy {

  private DstStringService service;

  public DstStringProxy(DstStringService service) {
    this.service = service;
  }

  public void put(String key, String value) {
    StringProtocol.StringPutRequest request =
        StringProtocol.StringPutRequest.newBuilder()
            .setKey(key)
            .setValue(value)
            .build();

    StringProtocol.StringPutResponse response = service.strPut(request);
    if (response.getStatus() != StatusProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public String get(String key) throws DstException {
    StringProtocol.StringGetRequest request =
        StringProtocol.StringGetRequest.newBuilder()
            .setKey(key)
            .build();

    StringProtocol.StringGetResponse response = service.strGet(request);
    if (response.getStatus() == StatusProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != StatusProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }

    return response.getValue();
  }
}
