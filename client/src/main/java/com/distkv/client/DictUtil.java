package com.distkv.client;

import com.distkv.rpc.protobuf.generated.DictProtocol;
import java.util.Map;

public class DictUtil {
  public static DictProtocol.DistKVDict buildDistKVDict(Map<String, String> localDict) {
    DictProtocol.DistKVDict.Builder dictBuilder =
        DictProtocol.DistKVDict.newBuilder();
    for (Map.Entry<String, String> entry : localDict.entrySet()) {
      dictBuilder.addKeys(entry.getKey());
      dictBuilder.addValues(entry.getValue());
    }
    return dictBuilder.build();
  }
}
