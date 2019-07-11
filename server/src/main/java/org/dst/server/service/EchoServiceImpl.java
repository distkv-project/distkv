package org.dst.server.service;

public class EchoServiceImpl implements EchoService {
  @Override
  public EchoExample.EchoResponse echo(EchoExample.EchoRequest request) {

    String message = request.getMessage();

    EchoExample.EchoResponse response = EchoExample.EchoResponse
            .newBuilder()
            .setMessage(message)
            .setMessage("Hefei Ubiversity of Technology!")
            .build();

    return response;
  }
}
