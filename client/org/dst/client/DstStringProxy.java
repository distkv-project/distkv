package org.dst.client;

import org.dst.client.exception.DstException;
import org.dst.client.exception.KeyNotFoundException;
import org.dst.server.generated.StringProtocol;
import org.dst.server.service.DstStringService;

public class DstStringProxy {

    private DstStringService service;

    public DstStringProxy(DstStringService service) {
        this.service = service;
    }

    public void put(String key, String value) {
        StringProtocol.StringPutRequest request =
                StringProtocol.StringPutRequest.newBuilder()
                        .setKey(key)
                        .setValue(value)
                        .build();

        StringProtocol.StringPutResponse response = service.strPut(request);
        if ("ok".equals(response.getStatus())) {
            throw new DstException("Unknown error.");
        }
    }

    public String get(String key) throws DstException {
       StringProtocol.StringGetRequest request =
               StringProtocol.StringGetRequest.newBuilder()
                       .setKey(key)
                       .build();

       StringProtocol.StringGetResponse response = service.strGet(request);
       // TODO(qwang): Refine this with enum `Status`.
       if ("ok" != response.getStatus()) {
           throw new KeyNotFoundException(key);
       }

       return response.getValue();
    }
}
