package com.distkv.dst.server.runtime.workerpool;

import com.distkv.dst.common.NodeInfo;
import com.distkv.dst.common.RequestTypeEnum;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.core.KVStoreImpl;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

public class Worker extends Thread {

  private NodeInfo nodeInfo;

  private NodeInstance nodeInstance;

  private static Logger LOGGER = LoggerFactory.getLogger(Worker.class);

  /**
   * The blocking queue to used for queue the requests.
   */
  // TODO(qwang): Remove the class DstConcurrentQueue.
  //private DstConcurrentQueue<Object> queue;
  private BlockingQueue<InternalRequest> queue;

  public void post(InternalRequest internalRequest) throws InterruptedException {
    queue.put(internalRequest);
  }

  /**
   * Store engine.
   */
  private KVStore storeEngine = new KVStoreImpl();

  @Override
  public void run() {
    while (true) {
      try {
        InternalRequest internalRequest = queue.take();
        switch (internalRequest.requestType) {
          case STR_PUT:
          {
            StringProtocol.PutRequest strPutRequest = (StringProtocol.PutRequest) internalRequest.request;
            // try put.
            storeEngine.strs().put(strPutRequest.getKey(), strPutRequest.getValue());
            CompletableFuture<StringProtocol.PutResponse> future = (CompletableFuture<StringProtocol.PutResponse>) internalRequest.completableFuture;
            future.complete(StringProtocol.PutResponse.newBuilder().setStatus(CommonProtocol.Status.OK).build());
          }
          case STR_GET:
          {
            StringProtocol.GetRequest strGetRequest = (StringProtocol.GetRequest) internalRequest.request;
            String value = storeEngine.strs().get(strGetRequest.getKey());
            CompletableFuture<StringProtocol.GetResponse> future = (CompletableFuture<StringProtocol.GetResponse>) internalRequest.completableFuture;
            StringProtocol.GetResponse response = StringProtocol.GetResponse.newBuilder()
                .setStatus(CommonProtocol.Status.OK)
                .setValue(value)
                .build();
            future.complete(response);
          }
        }
      } catch (Exception e) {
        LOGGER.error("Failed to execute event loop.");
      }
    }
  }

  private class InternalRequest {

    private RequestTypeEnum requestType;

    private Object request;

    private Object completableFuture;

    public InternalRequest(RequestTypeEnum requestType, Object request, Object completableFuture) {
      this.requestType = requestType;
      this.request = request;
      this.completableFuture = completableFuture;
    }
  }

}
