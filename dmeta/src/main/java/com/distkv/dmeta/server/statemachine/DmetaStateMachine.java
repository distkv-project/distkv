package com.distkv.dmeta.server.statemachine;

import com.alipay.remoting.exception.CodecException;
import com.alipay.remoting.serialization.SerializerManager;
import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Iterator;
import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.core.StateMachineAdapter;
import com.alipay.sofa.jraft.error.RaftException;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotReader;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotWriter;
import com.distkv.dmeta.server.DmetaStoreClosure;
import com.distkv.dmeta.server.bean.PutKVRequest;
import com.distkv.dmeta.server.bean.PutKVType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;


public class DmetaStateMachine extends StateMachineAdapter {

  private static final Logger LOG = LoggerFactory.getLogger(DmetaStateMachine.class);

  /**
   * Dmeta store
   */
  private final NameSpace store = new NameSpace();
  /**
   * Leader term
   */
  private final AtomicLong leaderTerm = new AtomicLong(-1);

  public boolean isLeader() {
    return this.leaderTerm.get() > 0;
  }

  /**
   * Returns Store Map.
   */
  public NameSpace getStore() {
    return this.store;
  }

  public String getValueByPath(String path) throws Exception {
    String[] names = path.split("&");
    SpaceInterface spi = store;
    for (int i = 0; i < names.length; i++) {
      spi = ((NameSpace) spi).getCurrentMap().get(names[i]);
    }
    return ((DataSpace) spi).getValue();
  }

  public void putKVByPath(String path, String key, String value) throws Exception {
    String[] names = path.split("&");
    SpaceInterface spi = store;
    for (int i = 0; i < names.length; i++) {
      spi = ((NameSpace) spi).getCurrentMap().get(names[i]);
    }
    ((NameSpace) spi).getCurrentMap().put(key, new DataSpace(value));
  }

  public void createPath(String path) throws Exception {
    String[] names = path.split("&");
    SpaceInterface spi = store;
    for (int i = 0; i < names.length; i++) {
      SpaceInterface tmpSpi = ((NameSpace) spi).getCurrentMap().get(names[i]);
      if (tmpSpi == null) {
        SpaceInterface ttSpi = new NameSpace();
        ((NameSpace) spi).getCurrentMap().put(names[i], ttSpi);
        spi = ttSpi;
      } else {
        spi = tmpSpi;
      }
    }
  }

  @Override
  public void onApply(final Iterator iter) {
    while (iter.hasNext()) {
      String key = null;
      String value = null;
      String path = null;
      int type = PutKVType.PUT_KV;

      DmetaStoreClosure closure = null;
      //iter.done() != null means this is leader
      if (iter.done() != null) {
        // This task is applied by this node, get value from closure to avoid additional parsing.
        closure = (DmetaStoreClosure) iter.done();
        key = closure.getRequest().getKey();
        value = closure.getRequest().getValue();
        path = closure.getRequest().getPath();
        type = closure.getRequest().getType();
      } else {
        // Have to parse FetchAddRequest from this user log.
        final ByteBuffer data = iter.getData();
        try {
          final PutKVRequest request = SerializerManager.getSerializer(SerializerManager.Hessian2)
              .deserialize(data.array(), PutKVRequest.class.getName());
          key = request.getKey();
          value = request.getValue();
          path = request.getPath();
          type = request.getType();
        } catch (final CodecException e) {
          LOG.error("Fail to decode IncrementAndGetRequest", e);
        }
      }

      try {
        switch (type) {
          default:
          case PutKVType.PUT_KV:
            putKVByPath(path, key, value);
            break;
          case PutKVType.CREATE_PATH:
            createPath(path);
            break;
        }
      } catch (Exception e) {
        closure.getResponse().setSuccess(false);
        closure.run(Status.OK());
      }

      if (closure != null) {
        closure.getResponse().setSuccess(true);
        closure.run(Status.OK());
      }
      LOG.info("Added value={} by key={} at logIndex={}", value, key, iter.getIndex());
      iter.next();
    }
  }

  @Override
  public void onSnapshotSave(final SnapshotWriter writer, final Closure done) {
    //    final ConcurrentHashMap<String, String> currVal = this.getStore();
    //    Utils.runInThread(() -> {
    //      final DmetaSnapshotFile snapshot =
    //          new DmetaSnapshotFile(writer.getPath() + File.separator + "data");
    //      if (snapshot.save(currVal)) {
    //        if (writer.addFile("data")) {
    //          done.run(Status.OK());
    //        } else {
    //          done.run(new Status(RaftError.EIO, "Fail to add file to writer"));
    //        }
    //      } else {
    //        done.run(new Status(RaftError.EIO, "Fail to save snapshot %s", snapshot.getPath()));
    //      }
    //    });
    // TODO(kairbon): add snapshot
  }

  @Override
  public void onError(final RaftException e) {
    LOG.error("Raft error: %s", e, e);
  }

  @Override
  public boolean onSnapshotLoad(final SnapshotReader reader) {
    //    if (isLeader()) {
    //      LOG.warn("Leader is not supposed to load snapshot");
    //      return false;
    //    }
    //    if (reader.getFileMeta("data") == null) {
    //      LOG.error("Fail to find data file in {}", reader.getPath());
    //      return false;
    //    }
    //    final DmetaSnapshotFile snapshot =
    //        new DmetaSnapshotFile(reader.getPath() + File.separator + "data");
    //    try {
    //      this.storeMap.putAll(snapshot.load());
    //      return true;
    //    } catch (final IOException e) {
    //      LOG.error("Fail to load snapshot from {}", snapshot.getPath());
    //      return false;
    //    } catch (CodecException e) {
    //      LOG.error("Fail to decode snapshot from {}", snapshot.getPath());
    //      return false;
    //    }
    // TODO(kairbon): add snapshot
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