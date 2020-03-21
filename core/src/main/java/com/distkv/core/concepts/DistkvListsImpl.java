package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvListIndexOutOfBoundsException;
import com.distkv.common.exception.DistkvUnknownRequestException;
import com.distkv.common.exception.DistkvWrongRequestFormatException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.core.DistkvMapInterface;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.distkv.rpc.protobuf.generated.ListProtocol.GetType;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListGetRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListGetResponse;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListLPutRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListMRemoveRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListPutRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListRPutRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol.ListRemoveRequest;
import com.distkv.rpc.protobuf.generated.ListProtocol.RemoveType;
import com.google.common.base.Preconditions;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DistkvListsImpl extends DistkvConcepts<ArrayList<String>>
    implements DistkvLists<ArrayList<String>> {

  private static Logger LOGGER = LoggerFactory.getLogger(DistkvListsImpl.class);

  public DistkvListsImpl(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }

  @Override
  public void get(String key, Builder builder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String get(String key, int index) throws DistkvException {
    try {
      final List<String> list = get(key);
      return list.get(index);
    } catch (IndexOutOfBoundsException e) {
      throw new DistkvListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public List<String> get(String key, int from, int end) throws DistkvException {
    try {
      final List<String> list = get(key);
      return list.subList(from, end);
    } catch (IndexOutOfBoundsException e) {
      throw new DistkvListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public void get(String key, Any requestBody, Builder builder) throws DistkvException {

    try {
      ListGetRequest listGetRequest = requestBody.unpack(ListGetRequest.class);
      final GetType type = listGetRequest.getType();
      ListGetResponse.Builder listBuilder = ListGetResponse.newBuilder();
      if (type == GetType.GET_ALL) {
        final List<String> values = get(key);
        listBuilder.addAllValues(values);
        builder.setResponse(Any.pack(listBuilder.build()));
      } else if (type == GetType.GET_ONE) {
        Preconditions.checkState(listGetRequest.getIndex() >= 0);
        listBuilder.addValues(get(key, listGetRequest.getIndex()));
        builder.setResponse(Any.pack(listBuilder.build()));
      } else if (type == GetType.GET_RANGE) {
        final List<String> values = get(key, listGetRequest.getFrom(), listGetRequest.getEnd());
        listBuilder.addAllValues(values);
        builder.setResponse(Any.pack(listBuilder.build()));
      } else {
        throw new DistkvUnknownRequestException(type.name());
      }
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public void put(String key, Any requestBody, Builder builder) throws DistkvException {

    try {
      ListPutRequest listPutRequest = requestBody.unpack(ListPutRequest.class);
      // TODO(qwang): Avoid this copy. See the discussion
      // at https://github.com/distkv-project/distkv/issues/349
      ArrayList<String> values = new ArrayList<>(listPutRequest.getValuesList());
      put(key, values);
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public void lput(String key, Any requestBody) throws DistkvException {
    try {
      ListLPutRequest listLPutRequest = requestBody.unpack(ListLPutRequest.class);
      lput(key, listLPutRequest.getValuesList());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public void lput(String key, List<String> value) throws DistkvException {
    get(key).addAll(0, value);
  }

  @Override
  public void rput(String key, Any requestBody, Builder builder)
      throws DistkvException {

    try {
      ListRPutRequest listRPutRequest = requestBody.unpack(ListRPutRequest.class);
      rput(key, listRPutRequest.getValuesList());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  public void rput(String key, List<String> value) throws DistkvException {
    get(key).addAll(value);
  }

  @Override
  public void remove(String key, Any requestBody, Builder builder)
      throws DistkvException {

    try {
      ListRemoveRequest listRemoveRequest = requestBody.unpack(ListRemoveRequest.class);
      final RemoveType type = listRemoveRequest.getType();
      if (type == RemoveType.RemoveOne) {
        remove(key, listRemoveRequest.getIndex());
      } else if (type == RemoveType.RemoveRange) {
        remove(key, listRemoveRequest.getFrom(), listRemoveRequest.getEnd());
      }
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public void remove(String key, int from, int end) throws DistkvException {
    try {
      List<String> list = get(key);
      list.subList(from, end).clear();
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DistkvListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public void remove(String key, int index) throws DistkvException {
    try {
      List<String> list = get(key);
      list.remove(index);
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DistkvListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public void mremove(String key, Any requestBody, Builder builder) throws DistkvException {
    try {
      ListMRemoveRequest listMRemoveRequest = requestBody.unpack(ListMRemoveRequest.class);
      mremove(key, listMRemoveRequest.getIndexesList());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

  @Override
  public void mremove(String key, List<Integer> indexes) throws DistkvException {
    final List<String> list = get(key);
    final Set<Integer> set = new HashSet<>();
    for (final Integer index : indexes) {
      if (index >= list.size()) {
        throw new DistkvListIndexOutOfBoundsException(key);
      }
      set.add(index);
    }

    final ArrayList<String> tempList = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      if (!set.contains(i)) {
        tempList.add(list.get(i));
      }
    }
    list.clear();
    list.addAll(tempList);
  }
}
