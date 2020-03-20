package com.distkv.core.concepts;


import com.distkv.common.exception.DistkvException;
import com.distkv.common.exception.DistkvKeyDuplicatedException;
import com.distkv.common.exception.KeyNotFoundException;
import com.distkv.common.utils.Status;
import com.distkv.core.DistkvMapInterface;
import com.distkv.rpc.protobuf.generated.CommonProtocol;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvResponse.Builder;
import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistkvStringsImpl extends DistkvConcepts<String>
    implements DistkvStrings<String> {

  private static Logger LOGGER = LoggerFactory.getLogger(DistkvStringsImpl.class);

  public DistkvStringsImpl(DistkvMapInterface<String, Object> distkvKeyValueMap) {
    super(distkvKeyValueMap);
  }

  @Override
  public void get(String key, Builder builder) {

    try {
      String value = get(key);
      StringProtocol.StrGetResponse strBuilder = StringProtocol.StrGetResponse
          .newBuilder().setValue(value).build();
      builder.setStatus(CommonProtocol.Status.OK).setResponse(Any.pack(strBuilder));
    } catch (KeyNotFoundException e) {
      builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
    }
  }

  @Override
  public void put(String key, Any requestBody, Builder builder)
      throws InvalidProtocolBufferException {
    StringProtocol.StrPutRequest strPutRequest = requestBody
        .unpack(StringProtocol.StrPutRequest.class);
    try {
      put(key, strPutRequest.getValue());
      builder.setStatus(CommonProtocol.Status.OK);
    } catch (DistkvKeyDuplicatedException e) {
      builder.setStatus(CommonProtocol.Status.DUPLICATED_KEY);
    }
  }

  @Override
  public void drop(String key, Builder builder) {

    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus = drop(key);
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (DistkvException e) {
      LOGGER.error("Failed to drop a string to store :{1}", e);
    }
    builder.setStatus(status);
  }
}
