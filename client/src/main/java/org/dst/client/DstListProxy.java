package org.dst.client;

import org.dst.common.exception.DstException;
import org.dst.common.exception.KeyNotFoundException;
import org.dst.rpc.protobuf.generated.CommonProtocol;
import org.dst.rpc.protobuf.generated.ListProtocol;
import org.dst.rpc.service.DstListService;
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
        .setType(ListProtocol.GetType.GET_ALL)
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

  public List<String> get(String key, Integer index) {
    ListProtocol.GetRequest request = ListProtocol.GetRequest.newBuilder()
            .setType(ListProtocol.GetType.GET_ONE)
            .setKey(key)
            .setIndex(index)
            .build();
    ListProtocol.GetResponse response = service.get(request);
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d",response.getStatus().getNumber()));
    }
    return response.getValuesList();
  }

  public List<String> get(String key, Integer from, Integer end) {
    ListProtocol.GetRequest request = ListProtocol.GetRequest.newBuilder()
            .setType(ListProtocol.GetType.GET_RANGE)
            .setKey(key)
            .setFrom(from)
            .setEnd(end)
            .build();
    ListProtocol.GetResponse response = service.get(request);
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d",response.getStatus().getNumber()));
    }
    return response.getValuesList();
  }

  public void drop(String key) {
    CommonProtocol.DropRequest request = CommonProtocol.DropRequest.newBuilder()
          .setKey(key)
          .build();
    CommonProtocol.DropResponse response = service.drop(request);
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

  public void remove(String key, Integer index) {
    ListProtocol.RemoveRequest request = ListProtocol.RemoveRequest.newBuilder()
            .setType(ListProtocol.RemoveType.RemoveOne)
            .setKey(key)
            .setIndex(index)
            .build();
    ListProtocol.RemoveResponse response = service.remove(request);
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public void remove(String key, Integer from, Integer end) {
    ListProtocol.RemoveRequest request = ListProtocol.RemoveRequest.newBuilder()
            .setType(ListProtocol.RemoveType.RemoveRange)
            .setKey(key)
            .setFrom(from)
            .setEnd(end)
            .build();
    ListProtocol.RemoveResponse response = service.remove(request);
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }

  public void multipleRemove(String key, List<Integer> indexes) {
    ListProtocol.MRemoveRequest request = ListProtocol.MRemoveRequest.newBuilder()
          .setKey(key)
          .addAllIndexes(indexes)
          .build();
    ListProtocol.MRemoveResponse response = service.multipleRemove(request);
    if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
      throw new KeyNotFoundException(key);
    } else if (response.getStatus() != CommonProtocol.Status.OK) {
      throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
    }
  }
}
