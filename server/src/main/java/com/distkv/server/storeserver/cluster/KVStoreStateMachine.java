package com.distkv.server.storeserver.cluster;

import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Iterator;
import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.core.StateMachineAdapter;
import com.alipay.sofa.jraft.error.RaftException;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotReader;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotWriter;
import com.distkv.rpc.protobuf.generated.DistkvProtocol;
import com.distkv.server.storeserver.StoreConfig;
import com.distkv.server.storeserver.runtime.StoreRuntime;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author : kairbon
 * @date : 2021/3/19
 */

public class KVStoreStateMachine extends StateMachineAdapter {

  private static final Logger LOG = LoggerFactory.getLogger(KVStoreStateMachine.class);

  /**
   * Leader term
   */
  private final AtomicLong leaderTerm = new AtomicLong(-1);

  /**
   * store runtime
   */
  private StoreRuntime storeRuntime;

  public boolean isLeader() {
    return this.leaderTerm.get() > 0;
  }

  public KVStoreStateMachine(StoreConfig config) {
    this.storeRuntime = new StoreRuntime(config);
  }


  @Override
  public void onApply(Iterator iter) {
    while (iter.hasNext()) {
      long current = 0;

      KVClosure closure = null;
      DistkvProtocol.DistkvRequest request = null;
      CompletableFuture<DistkvProtocol.DistkvResponse> future = null;

      if (iter.done() != null) {
        // This task is applied by this node, get value from closure to avoid additional parsing.
        closure = (KVClosure) iter.done();
        request = closure.getRequest();
        future = closure.getFuture();
      } else {
        // Have to parse FetchAddRequest from this user log.
        final ByteBuffer data = iter.getData();
        try {
          request = DistkvProtocol.DistkvRequest.parseFrom(data.array());
          future = new CompletableFuture<>();
        } catch (InvalidProtocolBufferException e) {
          e.printStackTrace();
        }
      }
      if (request != null) {
        try {
          storeRuntime.getWorkerPool().postRequest(request, future);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      iter.next();
    }


  }

  @Override
  public void onLeaderStart(final long term) {
    this.leaderTerm.set(term);
    super.onLeaderStart(term);

  }

  @Override
  public void onLeaderStop(final Status status) {
    this.leaderTerm.set(-1);
    super.onLeaderStop(status);
  }

  @Override
  public void onError(final RaftException e) {
    LOG.error("Raft error: {}", e, e);
  }

  @Override
  public void onSnapshotSave(SnapshotWriter writer, Closure done) {
  }

  @Override
  public boolean onSnapshotLoad(SnapshotReader reader) {
    return false;
  }
}
