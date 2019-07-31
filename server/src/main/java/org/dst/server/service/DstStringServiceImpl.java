package org.dst.server.service;

import org.dst.server.generated.DstServerProtocol;

public class DstStringServiceImpl implements DstStringService {

  @Override
  public DstServerProtocol.StringResponse handleString(DstServerProtocol.StringRequest request) {
    DstServerProtocol.StringResponse.Builder responseBuilder = DstServerProtocol.StringResponse.newBuilder();
    if (request.getRequestType() == DstServerProtocol.StringRequestType.PUT) {
      // ... PUT
      responseBuilder.setResponseType(DstServerProtocol.StringResponseType.STATUS_RESPONSE);
      responseBuilder.setResult("ok");
    }

    if (request.getRequestType() == DstServerProtocol.StringRequestType.GET) {
      responseBuilder.setResponseType(DstServerProtocol.StringResponseType.DATA_RESPONSE);
      responseBuilder.setResult("10000");
    }

    return responseBuilder.build();
  }
}
