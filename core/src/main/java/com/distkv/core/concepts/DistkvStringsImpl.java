package com.distkv.core.concepts;


import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvWrongRequestFormatException;
import com.distkv.core.DistkvMapInterface;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.distkv.rpc.protobuf.generated.StringProtocol.StrGetResponse;
import com.distkv.rpc.protobuf.generated.StringProtocol.StrPutRequest;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;

public class DistkvStringsImpl extends DistkvConcepts<String>
    implements DistkvStrings {

  public DistkvStringsImpl(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }

  @Override
  public void get(String key, Builder builder) throws DistkvException {
    String value = get(key);
    StrGetResponse strBuilder = StrGetResponse.newBuilder().setValue(value).build();
    builder.setResponse(Any.pack(strBuilder));
  }

  @Override
  public void put(String key, Any request) throws DistkvException {
    try {
      StrPutRequest strPutRequest = request.unpack(StrPutRequest.class);
      put(key, strPutRequest.getValue());
    } catch (InvalidProtocolBufferException e) {
      throw new DistkvWrongRequestFormatException(key, e);
    }
  }

}
