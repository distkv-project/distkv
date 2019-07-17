package org.dst.server.service;

import org.dst.server.generated.EchoExample;

public class EchoServiceImpl implements EchoService {
  @Override
  public EchoExample.EchoResponse echo(EchoExample.EchoRequest request) {

    String message = request.getMessage();

    EchoExample.EchoResponse response = EchoExample.EchoResponse
            .newBuilder()
            .setMessage(message)
            .build();

    return response;
  }
}
