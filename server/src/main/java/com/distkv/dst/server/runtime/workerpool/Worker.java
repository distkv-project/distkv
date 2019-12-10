package com.distkv.dst.server.runtime.workerpool;

import com.distkv.dst.common.NodeInfo;
import com.distkv.dst.core.KVStore;
import com.distkv.dst.core.KVStoreImpl;
import com.distkv.dst.rpc.protobuf.generated.CommonProtocol;
import com.distkv.dst.rpc.protobuf.generated.StringProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

public class Worker extends Thread {

  private NodeInfo nodeInfo;

  private NodeInstance nodeInstance;

  private static Logger LOGGER = LoggerFactory.getLogger(Worker.class);

  public Worker() {
    queue = new LinkedBlockingQueue<>();
  }

  private BlockingQueue<InternalRequest> queue;

  // Note that this method is threading-safe.
  public void post(InternalRequest internalRequest) throws InterruptedException {
    queue.put(internalRequest);
  }

  /**
   * Store engine.
   */
  private KVStore storeEngine = new KVStoreImpl();

  @SuppressWarnings({"unchecked"})
  @Override
  public void run() {
    while (true) {
      try {
        InternalRequest internalRequest = queue.take();
        switch (internalRequest.getRequestType()) {
          case STR_PUT:
          {
            StringProtocol.PutRequest strPutRequest =
                (StringProtocol.PutRequest) internalRequest.getRequest();
            // try put.
            storeEngine.strs().put(strPutRequest.getKey(), strPutRequest.getValue());
            CompletableFuture<StringProtocol.PutResponse> future =
                (CompletableFuture<StringProtocol.PutResponse>)
                    internalRequest.getCompletableFuture();
            future.complete(StringProtocol.PutResponse.newBuilder()
                .setStatus(CommonProtocol.Status.OK).build());
            break;
          }
          case STR_GET:
          {
            StringProtocol.GetRequest strGetRequest = (StringProtocol.GetRequest)
                internalRequest.getRequest();
            String value = storeEngine.strs().get(strGetRequest.getKey());
            StringProtocol.GetResponse.Builder builder = StringProtocol.GetResponse.newBuilder();
            if (value == null) {
              builder.setStatus(CommonProtocol.Status.KEY_NOT_FOUND);
            } else {
              builder.setStatus(CommonProtocol.Status.OK).setValue(value);
            }
            CompletableFuture<StringProtocol.GetResponse> future =
                (CompletableFuture<StringProtocol.GetResponse>)
                    internalRequest.getCompletableFuture();
            future.complete(builder.build());
            break;
          }
          default:
          {

          }
        }
      } catch (Exception e) {
        LOGGER.error("Failed to execute event loop:" + e);
      }
    }
  }

}
