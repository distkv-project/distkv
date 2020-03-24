package com.distkv.server.metaserver.server.statemachine;

import com.alipay.remoting.exception.CodecException;
import com.alipay.remoting.serialization.SerializerManager;
import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Iterator;
import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.core.StateMachineAdapter;
import com.alipay.sofa.jraft.error.RaftException;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotReader;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotWriter;
import com.distkv.common.NodeInfo;
import com.distkv.server.metaserver.server.DmetaStoreClosure;
import com.distkv.server.metaserver.server.bean.HeartBeatRequest;
import com.distkv.server.view.NodeTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;


public class MetaStateMachine extends StateMachineAdapter {

  private static final Logger LOG = LoggerFactory.getLogger(MetaStateMachine.class);

  /**
   * The global view of a Distkv cluster.
   * <p>
   * TODO(qwang): Thread safe?
   */
  private NodeTable nodeTable = new NodeTable();

  /**
   * Leader term.
   */
  private final AtomicLong leaderTerm = new AtomicLong(-1);

  public boolean isLeader() {
    return 0 < this.leaderTerm.get();
  }

  public NodeTable getNodeTable() {
    return this.nodeTable;
  }

  @Override
  public void onApply(final Iterator iter) {
    while (iter.hasNext()) {
      NodeInfo nodeInfo = null;

      DmetaStoreClosure doneClosure = null;
      if (iter.done() != null) {
        // The done closure of current iter is not null, so this is the code path of leader.
        //
        // Note that this task is applied by this node, no need to parse anything data,
        // just read it, because this is the task is from client side, it's not a sync task
        // of leader-follow.
        doneClosure = (DmetaStoreClosure) iter.done();
        nodeInfo = doneClosure.getRequest().getNodeInfo();
      } else {
        // Have to parse FetchAddRequest from this user log.
        final ByteBuffer data = iter.getData();
        try {
          final HeartBeatRequest request = SerializerManager
              .getSerializer(SerializerManager.Hessian2)
              .deserialize(data.array(), HeartBeatRequest.class.getName());
          nodeInfo = request.getNodeInfo();
        } catch (final CodecException e) {
          // TODO(qwang): How to handle this error?
          LOG.error("Fail to decode IncrementAndGetRequest", e);
        }
      }

      try {
        nodeTable.put(nodeInfo);
      } catch (Exception e) {
        e.printStackTrace();
        if (doneClosure != null) {
          doneClosure.getResponse().setSuccess(false);
        }
        LOG.error("Added node fail", nodeInfo.getNodeName());
      }

      if (doneClosure != null) {
        doneClosure.getResponse().setSuccess(true);
        doneClosure.getResponse().setNodeTable(nodeTable.getMap());
        doneClosure.run(Status.OK());
      }
      iter.next();
    }
  }

  @Override
  public void onSnapshotSave(final SnapshotWriter writer, final Closure done) {
    // TODO(kairbon): Add snapshot
  }

  @Override
  public void onError(final RaftException e) {
    LOG.error("Raft error: %s", e);
  }

  @Override
  public boolean onSnapshotLoad(final SnapshotReader reader) {
    // TODO(kairbon): Add snapshot
    return false;
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

}
