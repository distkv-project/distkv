package org.dst.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.dst.core.KVStore;
import org.dst.exception.DstException;
import org.dst.exception.KeyNotFoundException;
import org.dst.server.base.DstBaseService;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.ListProtocol;
import org.dst.utils.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DstListServiceImpl extends DstBaseService implements DstListService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DstListServiceImpl.class);

  public DstListServiceImpl(KVStore store) {
    super(store);
  }

  @Override
  public ListProtocol.PutResponse put(ListProtocol.PutRequest request) {
    ListProtocol.PutResponse.Builder responseBuilder =
            ListProtocol.PutResponse.newBuilder();
    CommonProtocol.Status status;
    try {
      getStore().lists().put(request.getKey(),new ArrayList<>(request.getValuesList()));
      status = CommonProtocol.Status.OK;
    } catch (DstException e) {
      LOGGER.error("Failed to put a list to store: {}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.GetResponse get(ListProtocol.GetRequest request) {
    ListProtocol.GetResponse.Builder responseBuilder =
            ListProtocol.GetResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.OK;
    try {
      List<String> values = getStore().lists().get(request.getKey());
      Optional.ofNullable(values).ifPresent(v -> responseBuilder.addAllValues(values));
    } catch (KeyNotFoundException e) {
      LOGGER.error("Failed to get a list from store: {}", e);
      status = CommonProtocol.Status.KEY_NOT_FOUND;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.DelResponse del(ListProtocol.DelRequest request) {
    ListProtocol.DelResponse.Builder responseBuilder =
            ListProtocol.DelResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus = getStore().lists().del(request.getKey());
      if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      } else if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      }
    } catch (DstException e) {
      LOGGER.error("Failed to del a list from store: {}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.LPutResponse lput(ListProtocol.LPutRequest request) {
    ListProtocol.LPutResponse.Builder responseBuilder =
            ListProtocol.LPutResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus = getStore().lists().lput(request.getKey(), request.getValuesList());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (DstException e) {
      LOGGER.error("Failed to lput a list to store: {}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.RPutResponse rput(ListProtocol.RPutRequest request) {
    ListProtocol.RPutResponse.Builder responseBuilder =
            ListProtocol.RPutResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus = getStore().lists().rput(request.getKey(), request.getValuesList());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (DstException e) {
      LOGGER.error("Failed to rput a list to store: {}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.LDelResponse ldel(ListProtocol.LDelRequest request) {
    ListProtocol.LDelResponse.Builder responseBuilder =
            ListProtocol.LDelResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus = getStore().lists().ldel(request.getKey(), request.getValues());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (DstException e) {
      LOGGER.error("Failed to ldel a list from store: {}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }

  @Override
  public ListProtocol.RDelResponse rdel(ListProtocol.RDelRequest request) {
    ListProtocol.RDelResponse.Builder responseBuilder =
            ListProtocol.RDelResponse.newBuilder();
    CommonProtocol.Status status = CommonProtocol.Status.UNKNOWN_ERROR;
    try {
      Status localStatus = getStore().lists().rdel(request.getKey(), request.getValues());
      if (localStatus == Status.OK) {
        status = CommonProtocol.Status.OK;
      } else if (localStatus == Status.KEY_NOT_FOUND) {
        status = CommonProtocol.Status.KEY_NOT_FOUND;
      }
    } catch (DstException e) {
      LOGGER.error("Failed to rdel a list from store: {}", e);
      status = CommonProtocol.Status.UNKNOWN_ERROR;
    }
    responseBuilder.setStatus(status);
    return responseBuilder.build();
  }
}
