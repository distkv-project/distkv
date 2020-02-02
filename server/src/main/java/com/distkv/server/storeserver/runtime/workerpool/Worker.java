package com.distkv.server.storeserver.runtime.workerpool;

//import com.distkv.common.DistKVTuple;
import com.distkv.common.NodeInfo;
//import com.distkv.common.entity.sortedList.SortedListEntity;
//import com.distkv.common.exception.DistKVException;
//import com.distkv.common.exception.SortedListTopNumIsNonNegativeException;
//import com.distkv.common.exception.DistKVListIndexOutOfBoundsException;
//import com.distkv.common.exception.KeyNotFoundException;
//import com.distkv.common.exception.SortedListMemberNotFoundException;
//import com.distkv.common.utils.Status;
import com.distkv.core.KVStore;
import com.distkv.core.KVStoreImpl;
//import com.distkv.rpc.protobuf.generated.CommonProtocol;
//import com.distkv.rpc.protobuf.generated.DictProtocol;
//import com.distkv.rpc.protobuf.generated.ListProtocol;
//import com.distkv.rpc.protobuf.generated.SetProtocol;
//import com.distkv.rpc.protobuf.generated.SortedListProtocol;
//import com.distkv.rpc.protobuf.generated.StringProtocol;
import com.distkv.server.storeserver.runtime.StoreRuntime;
import com.distkv.server.storeserver.runtime.slave.SlaveClient;
//import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.LinkedList;
import java.util.List;
//import java.util.ListIterator;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Set;
import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

public class Worker extends Thread {

  private NodeInfo nodeInfo;

  private NodeInstance nodeInstance;

  private StoreRuntime storeRuntime;

  private static Logger LOGGER = LoggerFactory.getLogger(Worker.class);

  public Worker(StoreRuntime storeRuntime) {
    this.storeRuntime = storeRuntime;
    queue = new LinkedBlockingQueue<>();
  }

  private BlockingQueue<InternalRequest> queue;

  // Note that this method is threading-safe because of the threading-safe blocking queue.
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
    // Whether this store instance is a master instance.
    final boolean isMaster = storeRuntime.getConfig().isMaster();
    final List<SlaveClient> slaveClients = storeRuntime.getAllSlaveClients();
    while (true) {
      try {
        InternalRequest internalRequest = queue.take();
        // switch (internalRequest.getRequestType()) {
      } catch (Throwable e) {
        LOGGER.error("Failed to execute event loop:" + e);
        // TODO(tuowang): Clean up some resource associated with StoreRuntime
        storeRuntime.shutdown();
        Runtime.getRuntime().exit(-1);
      }
    }
  }

}
