package com.distkv.dst.server.service;

import com.distkv.dst.common.utils.FutureUtils;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.DictProtocol;
import com.distkv.dst.rpc.service.DstDictService;
import com.distkv.dst.server.base.DstBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DstDictServiceImpl extends DstBaseService implements DstDictService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstDictServiceImpl.class);

  public DstDictServiceImpl(KVStore store) {
    super(store);
  }

  @Override
  public CompletableFuture<DictProtocol.PutResponse> put(
      DictProtocol.PutRequest request) {
    DictProtocol.PutResponse.Builder responseBuilder =
          DictProtocol.PutResponse.newBuilder();
    try {
      final Map<String, String> map = new HashMap<>();
      DictProtocol.DstDict dstDict = request.getDict();
      for (int i = 0; i < dstDict.getKeysCount(); i++) {
        map.put(dstDict.getKeys(i), dstDict.getValues(i));
      }
      getStore().dicts().put(request.getKey(), map);
      responseBuilder.setStatus(CommonProtocol.Status.OK);
    } catch (Exception e) {
      // TODO(qwang): Use DstException instead of Exception here.
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    }

    return FutureUtils.newCompletableFuture(responseBuilder.build());
  }

  @Override
  public CompletableFuture<DictProtocol.GetResponse> get(
      DictProtocol.GetRequest request) {
    DictProtocol.GetResponse.Builder responseBuilder =
          DictProtocol.GetResponse.newBuilder();
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    final Map<String, String> dict = getStore().dicts().get(request.getKey());
    if (dict == null) {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
      return FutureUtils.newCompletableFuture(responseBuilder.build());
    }
    DictProtocol.DstDict.Builder builder = DictProtocol.DstDict.newBuilder();
    for (Map.Entry<String, String> entry : dict.entrySet()) {
      builder.addKeys(entry.getKey());
      builder.addValues(entry.getValue());
    }
    responseBuilder.setDict(builder.build());
    return FutureUtils.newCompletableFuture(responseBuilder.build());
  }

  @Override
  public CompletableFuture<DictProtocol.GetItemResponse> getItemValue(
      DictProtocol.GetItemRequest request) {
    DictProtocol.GetItemResponse.Builder responseBuilder =
          DictProtocol.GetItemResponse.newBuilder();
    final Map<String, String> dict = getStore().dicts().get(request.getKey());
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    if (dict == null) {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
      return FutureUtils.newCompletableFuture(responseBuilder.build());
    }
    final String itemValue = dict.get(request.getItemKey());
    if (itemValue == null) {
      responseBuilder.setStatus(CommonProtocol.Status.DICT_KEY_NOT_FOUND);
      return FutureUtils.newCompletableFuture(responseBuilder.build());
    }
    responseBuilder.setItemValue(itemValue);
    return FutureUtils.newCompletableFuture(responseBuilder.build());
  }

  @Override
  public CompletableFuture<DictProtocol.PopItemResponse> popItem(
      DictProtocol.PopItemRequest request) {
    DictProtocol.PopItemResponse.Builder responseBuilder =
          DictProtocol.PopItemResponse.newBuilder();
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    final Map<String, String> dict = getStore().dicts().get(request.getKey());
    if (dict == null) {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
      return FutureUtils.newCompletableFuture(responseBuilder.build());
    }
    final String itemValue = dict.remove(request.getItemKey());
    if (itemValue == null) {
      responseBuilder.setStatus(CommonProtocol.Status.DICT_KEY_NOT_FOUND);
      return FutureUtils.newCompletableFuture(responseBuilder.build());
    } else {
      responseBuilder.setItemValue(itemValue);
    }
    return FutureUtils.newCompletableFuture(responseBuilder.build());
  }

  @Override
  public CompletableFuture<DictProtocol.PutItemResponse> putItem(
      DictProtocol.PutItemRequest request) {
    DictProtocol.PutItemResponse.Builder responseBuilder =
          DictProtocol.PutItemResponse.newBuilder();
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    final Map<String, String> dict = getStore().dicts().get(request.getKey());
    if (dict == null) {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
      return FutureUtils.newCompletableFuture(responseBuilder.build());
    }
    dict.put(request.getItemKey(), request.getItemValue());
    return FutureUtils.newCompletableFuture(responseBuilder.build());
  }

  @Override
  public CompletableFuture<DictProtocol.RemoveItemResponse> removeItem(
      DictProtocol.RemoveItemRequest request) {
    DictProtocol.RemoveItemResponse.Builder responseBuilder =
          DictProtocol.RemoveItemResponse.newBuilder();
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    final Map<String, String> dict = getStore().dicts().get(request.getKey());
    if (dict == null) {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
      return FutureUtils.newCompletableFuture(responseBuilder.build());
    }
    final String itemValue = dict.remove(request.getItemKey());
    if (itemValue == null) {
      responseBuilder.setStatus(CommonProtocol.Status.DICT_KEY_NOT_FOUND);
      return FutureUtils.newCompletableFuture(responseBuilder.build());
    }
    dict.remove(request.getItemKey());
    return FutureUtils.newCompletableFuture(responseBuilder.build());
  }


  @Override
  public CompletableFuture<CommonProtocol.DropResponse> drop(
      CommonProtocol.DropRequest request) {
    CommonProtocol.DropResponse.Builder responseBuilder =
        CommonProtocol.DropResponse.newBuilder();
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    if (!getStore().dicts().drop(request.getKey())) {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    }
    return FutureUtils.newCompletableFuture(responseBuilder.build());
  }
}
