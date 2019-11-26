package com.distkv.dst.server.service;

import com.distkv.dst.core.KVStore;
import com.distkv.dst.server.base.DstBaseService;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import com.distkv.dst.rpc.service.DstStringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DstStringServiceImpl extends DstBaseService implements DstStringService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstStringServiceImpl.class);

  public DstStringServiceImpl(KVStore store) {
    super(store);
  }


  @Override
  public StringProtocol.PutResponse put(StringProtocol.PutRequest request) {
    StringProtocol.PutResponse.Builder responseBuilder =
            StringProtocol.PutResponse.newBuilder();
    getStore().strs().put(request.getKey(), request.getValue());
    responseBuilder.setStatus(CommonProtocol.Status.OK);
    return responseBuilder.build();
  }

  @Override
  public StringProtocol.GetResponse get(StringProtocol.GetRequest request) {
    StringProtocol.GetResponse.Builder responseBuilder =
            StringProtocol.GetResponse.newBuilder();

    String value = getStore().strs().get(request.getKey());
    if (value != null) {
      responseBuilder.setValue(getStore().strs().get(request.getKey()));
      responseBuilder.setStatus(CommonProtocol.Status.OK);
    } else {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    }
    return responseBuilder.build();
  }

  @Override
  public CommonProtocol.DropResponse drop(CommonProtocol.DropRequest request) {
    CommonProtocol.DropResponse.Builder responseBuilder =
            CommonProtocol.DropResponse.newBuilder();

    responseBuilder.setStatus(CommonProtocol.Status.OK);
    if (!getStore().strs().drop(request.getKey())) {
      responseBuilder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
      return responseBuilder.build();
    }
    return responseBuilder.build();
  }

}
