package org.dst.server.service;

import java.util.HashSet;
import java.util.Set;
import org.dst.core.KVStore;
import org.dst.server.base.DstBaseService;
import org.dst.server.generated.CommonProtocol;
import org.dst.server.generated.SetProtocol;

public class DstSetServiceImpl extends DstBaseService implements DstSetService {


  public DstSetServiceImpl(KVStore store) {
    super(store);
  }

  @Override
  public SetProtocol.PutResponse put(SetProtocol.PutRequest request) {
    SetProtocol.PutResponse.Builder setPutResponseBuilder =
            SetProtocol.PutResponse.newBuilder();

    store.sets().put(request.getKey(), new HashSet<>(request.getValuesList()));
    setPutResponseBuilder.setStatus(CommonProtocol.Status.OK);

    return setPutResponseBuilder.build();
  }

  @Override
  public SetProtocol.GetResponse get(SetProtocol.GetRequest request) {
    SetProtocol.GetResponse.Builder setGetResponseBuilder =
            SetProtocol.GetResponse.newBuilder();

    Set<String> values = store.sets().get(request.getKey());
    values.forEach(value -> setGetResponseBuilder.addValues(value));

    setGetResponseBuilder.setStatus(CommonProtocol.Status.OK);
    return setGetResponseBuilder.build();
  }
}
