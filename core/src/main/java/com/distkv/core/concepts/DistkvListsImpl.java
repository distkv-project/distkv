package com.distkv.core.concepts;

import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.exception.DistkvListIndexOutOfBoundsException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.Status;
import com.distkv.core.DistkvMapInterface;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.distkv.rpc.protobuf.generated.ListProtocol;
import com.google.common.base.Preconditions;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
  public String get(String key, int index)
      throws KeyNotFoundException, IndexOutOfBoundsException {
    try {
      final List<String> list = get(key);
      return list.get(index);
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DistkvListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public List<String> get(String key, int from, int end)
      throws KeyNotFoundException, IndexOutOfBoundsException {
    try {
      final List<String> list = get(key);
      return list.subList(from, end);
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DistkvListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public void get(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    ListProtocol.ListGetRequest listGetRequest = requestBody
        .unpack(ListProtocol.ListGetRequest.class);
    CommonProtocol.Status status = CommonProtocol.Status.OK;
    final ListProtocol.GetType type = listGetRequest.getType();
    try {
      ListProtocol.ListGetResponse.Builder listBuilder = ListProtocol.ListGetResponse
          .newBuilder();
      if (type == ListProtocol.GetType.GET_ALL) {
        final List<String> values = get(key);
        Optional.ofNullable(values).ifPresent(v -> listBuilder.addAllValues(values));
        builder.setResponse(Any.pack(listBuilder.build()));
      } else if (type == ListProtocol.GetType.GET_ONE) {
        Preconditions.checkState(listGetRequest.getIndex() >= 0);
        listBuilder.addValues(get(key, listGetRequest.getIndex()));
        builder.setResponse(Any.pack(listBuilder.build()));
      } else if (type == ListProtocol.GetType.GET_RANGE) {
        final List<String> values = get(
            key, listGetRequest.getFrom(), listGetRequest.getEnd());
        Optional.ofNullable(values).ifPresent(v -> listBuilder.addAllValues(values));
        builder.setResponse(Any.pack(listBuilder.build()));
      } else {
        LOGGER.error("Failed to get a list from store.");
        status = CommonProtocol.Status.UNKNOWN_REQUEST_TYPE;
      }
    } catch (KeyNotFoundException e) {
      LOGGER.info("Failed to get a list from store: {1}", e);
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DistkvListIndexOutOfBoundsException e) {
      LOGGER.info("Failed to get a list from store: {1}", e);
      status = CommonProtocol.Status.LIST_INDEX_OUT_OF_BOUNDS;
    }
    builder.setStatus(status);
  }

  @Override
  public void put(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    ListProtocol.ListPutRequest listPutRequest = requestBody
        .unpack(ListProtocol.ListPutRequest.class);
    CommonProtocol.Status status = CommonProtocol.Status.OK;
    try {
      // TODO(qwang): Avoid this copy. See the discussion
      // at https://github.com/distkv-project/distkv/issues/349
      ArrayList<String> values = new ArrayList<>(listPutRequest.getValuesList());
      put(key, values);
    } catch (DistkvKeyDuplicatedException e) {
      status = CommonProtocol.Status.DUPLICATED_KEY;
    } catch (DistkvException e) {
      LOGGER.error("Failed to put a list to store: {1}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    builder.setStatus(status);
  }


  @Override
  public void drop(String key, Builder builder) {

    CommonProtocol.Status status = null;
    try {
      Status localStatus = drop(key);
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (DistkvException e) {
      status = CommonProtocol.Status.UNKNOWN_ERROR;
      LOGGER.error("Failed to drop a list from store: {1}", e);
    }
    builder.setStatus(status);
  }

  @Override
  public void lput(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    ListProtocol.ListLPutRequest listLPutRequest = requestBody
        .unpack(ListProtocol.ListLPutRequest.class);
    CommonProtocol.Status status = null;
    try {
      Status localStatus = lput(key, listLPutRequest.getValuesList());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (DistkvException e) {
      status = CommonProtocol.Status.UNKNOWN_ERROR;
      LOGGER.error("Failed to lput a list to store: {1}", e);
    }
    builder.setStatus(status);
  }

  @Override
  public Status lput(String key, List<String> value) {
    if (!this.distkvKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }
    get(key).addAll(0, value);
    return Status.OK;
  }

  @Override
  public void rput(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    ListProtocol.ListRPutRequest listRPutRequest = requestBody
        .unpack(ListProtocol.ListRPutRequest.class);
    CommonProtocol.Status status = null;
    try {
      Status localStatus = rput(key, listRPutRequest.getValuesList());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (DistkvException e) {
      status = CommonProtocol.Status.UNKNOWN_ERROR;
      LOGGER.error("Failed to rput a list to store: {1}", e);
    }
    builder.setStatus(status);
  }

  public Status rput(String key, List<String> value) {
    if (!this.distkvKeyValueMap.containsKey(key)) {
      return Status.KEY_NOT_FOUND;
    }
    get(key).addAll(value);
    return Status.OK;
  }

  @Override
  public void remove(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {

    ListProtocol.ListRemoveRequest listRemoveRequest = requestBody
        .unpack(ListProtocol.ListRemoveRequest.class);
    CommonProtocol.Status status = null;
    final ListProtocol.RemoveType type = listRemoveRequest.getType();
    try {
      Status localStatus = null;
      if (type == ListProtocol.RemoveType.RemoveOne) {
        localStatus = remove(key, listRemoveRequest.getIndex());
        if (localStatus == Status.OK) {
          status = CommonProtocol.Status.OK;
        } else if (localStatus == Status.KEY_NOT_FOUND) {
          status = CommonProtocol.Status.KEY_NOT_FOUND;
        }
      } else if (type == ListProtocol.RemoveType.RemoveRange) {
        localStatus = remove(key, listRemoveRequest.getFrom(), listRemoveRequest.getEnd());
      }
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (KeyNotFoundException e) {
      LOGGER.info("Failed to remove item from store: {1}", e);
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DistkvListIndexOutOfBoundsException e) {
      LOGGER.info("Failed to remove item from store: {1}", e);
      status = CommonProtocol.Status.LIST_INDEX_OUT_OF_BOUNDS;
    }
    builder.setStatus(status);
  }

  @Override
  public Status remove(String key, int from, int end)
      throws KeyNotFoundException, DistkvListIndexOutOfBoundsException {
    try {
      List<String> list = get(key);
      list.subList(from, end).clear();
      return Status.OK;
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DistkvListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public Status remove(String key, int index)
      throws KeyNotFoundException, DistkvListIndexOutOfBoundsException {
    try {
      List<String> list = get(key);
      list.remove(index);
      return Status.OK;
    } catch (NullPointerException e) {
      throw new KeyNotFoundException(key);
    } catch (IndexOutOfBoundsException e) {
      throw new DistkvListIndexOutOfBoundsException(key, e);
    }
  }

  @Override
  public void mremove(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {
    ListProtocol.ListMRemoveRequest listMRemoveRequest = requestBody
        .unpack(ListProtocol.ListMRemoveRequest.class);
    CommonProtocol.Status status = null;
    try {
      Status localStatus = mremove(key, listMRemoveRequest.getIndexesList());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (KeyNotFoundException e) {
      LOGGER.info("Failed to mRemove item from store: {1}", e);
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    } catch (DistkvListIndexOutOfBoundsException e) {
      LOGGER.info("Failed to mRemove item from store: {1}", e);
      status = CommonProtocol.Status.LIST_INDEX_OUT_OF_BOUNDS;
    }
    builder.setStatus(status);
  }

  @Override
  public Status mremove(String key, List<Integer> indexes)
      throws KeyNotFoundException, DistkvListIndexOutOfBoundsException {
    final List<String> list = get(key);
    if (list == null) {
      throw new KeyNotFoundException(key);
    }

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
    return Status.OK;
  }
}
