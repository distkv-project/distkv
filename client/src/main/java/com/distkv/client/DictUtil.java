package com.distkv.client;

import com.distkv.rpc.protobuf.generated.DictProtocol;
import java.util.Map;

public class DictUtil {
  public static DictProtocol.DstDict buildDstDict(Map<String, String> localDict) {
    DictProtocol.DstDict.Builder dictBuilder =
        DictProtocol.DstDict.newBuilder();
    for (Map.Entry<String, String> entry : localDict.entrySet()) {
      dictBuilder.addKeys(entry.getKey());
      dictBuilder.addValues(entry.getValue());
    }
    return dictBuilder.build();
  }
}
