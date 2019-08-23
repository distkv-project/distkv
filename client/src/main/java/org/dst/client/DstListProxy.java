package org.dst.client;

import org.dst.exception.DstException;
import org.dst.exception.KeyNotFoundException;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.ListProtocol;
import org.dst.server.service.DstListService;
import java.util.List;

public class DstListProxy {

  private DstListService service;

  public DstListProxy(DstListService service) {
    this.service = service;
  }

  public void put(String key, List<String> value) {
    ListProtocol.PutRequest request = ListProtocol.PutRequest.newBuilder()
          .setKey(key)
          .addAllValues(value)
          .build();
    ListProtocol.PutResponse response = service.put(request);
    if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public List<String> get(String key) {
    ListProtocol.GetRequest request = ListProtocol.GetRequest.newBuilder()
          .setKey(key)
          .build();
    ListProtocol.GetResponse response = service.get(request);
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
    return response.getValuesList();
  }

  public void del(String key) {
    ListProtocol.DelRequest request = ListProtocol.DelRequest.newBuilder()
          .setKey(key)
          .build();
    ListProtocol.DelResponse response = service.del(request);
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public void lput(String key, List<String> value) {
    ListProtocol.LPutRequest request = ListProtocol.LPutRequest.newBuilder()
          .setKey(key)
          .addAllValues(value)
          .build();
    ListProtocol.LPutResponse response = service.lput(request);
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public void rput(String key, List<String> value) {
    ListProtocol.RPutRequest request = ListProtocol.RPutRequest.newBuilder()
          .setKey(key)
          .addAllValues(value)
          .build();
    ListProtocol.RPutResponse response = service.rput(request);
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public void ldel(String key, Integer index) {
    ListProtocol.LDelRequest request = ListProtocol.LDelRequest.newBuilder()
          .setKey(key)
          .setValues(index)
          .build();
    ListProtocol.LDelResponse response = service.ldel(request);
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public void rdel(String key, Integer index) {
    ListProtocol.RDelRequest request = ListProtocol.RDelRequest.newBuilder()
          .setKey(key)
          .setValues(index)
          .build();
    ListProtocol.RDelResponse response = service.rdel(request);
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }
}
