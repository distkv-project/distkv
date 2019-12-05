package com.distkv.dmeta.example.counter;

import com.alipay.remoting.exception.CodecException;
import com.alipay.remoting.serialization.SerializerManager;
import com.alipay.sofa.jraft.Closure;
import com.alipay.sofa.jraft.Iterator;
import com.alipay.sofa.jraft.Status;
import com.alipay.sofa.jraft.core.StateMachineAdapter;
import com.alipay.sofa.jraft.error.RaftError;
import com.alipay.sofa.jraft.error.RaftException;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotReader;
import com.alipay.sofa.jraft.storage.snapshot.SnapshotWriter;
import com.alipay.sofa.jraft.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.distkv.dmeta.example.counter.rpc.IncrementAndGetRequest;
import com.distkv.dmeta.example.counter.snapshot.CounterSnapshotFile;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

public class CounterStateMachine extends StateMachineAdapter {

  private static final Logger LOG = LoggerFactory.getLogger(CounterStateMachine.class);

  /**
   * Counter value
   */
  private final AtomicLong value = new AtomicLong(0);
  /**
   * Leader term
   */
  private final AtomicLong leaderTerm = new AtomicLong(-1);

  public boolean isLeader() {
    return this.leaderTerm.get() > 0;
  }

  /**
   * Returns current value.
   */
  public long getValue() {
    return this.value.get();
  }

  @Override
  public void onApply(final Iterator iter) {
    while (iter.hasNext()) {
      long delta = 0;

      IncrementAndAddClosure closure = null;
      //iter.done() != null means this is leader
      if (iter.done() != null) {
        // This task is applied by this node, get value from closure to avoid additional parsing.
        closure = (IncrementAndAddClosure) iter.done();
        delta = closure.getRequest().getDelta();
      } else {
        // Have to parse FetchAddRequest from this user log.
        final ByteBuffer data = iter.getData();
        try {
          final IncrementAndGetRequest request = SerializerManager
              .getSerializer(SerializerManager.Hessian2)
              .deserialize(data.array(), IncrementAndGetRequest.class.getName());
          delta = request.getDelta();
        } catch (final CodecException e) {
          LOG.error("Fail to decode IncrementAndGetRequest", e);
        }
      }
      final long prev = this.value.get();
      final long updated = value.addAndGet(delta);
      if (closure != null) {
        closure.getResponse().setValue(updated);
        closure.getResponse().setSuccess(true);
        closure.run(Status.OK());
      }
      LOG.info("Added value={} by delta={} at logIndex={}", prev, delta, iter.getIndex());
      iter.next();
    }
  }

  @Override
  public void onSnapshotSave(final SnapshotWriter writer, final Closure done) {
    final long currVal = this.value.get();
    Utils.runInThread(() -> {
      final CounterSnapshotFile snapshot =
          new CounterSnapshotFile(writer.getPath() + File.separator + "data");
      if (snapshot.save(currVal)) {
        if (writer.addFile("data")) {
          done.run(Status.OK());
        } else {
          done.run(new Status(RaftError.EIO, "Fail to add file to writer"));
        }
      } else {
        done.run(new Status(RaftError.EIO, "Fail to save snapshot %s", snapshot.getPath()));
      }
    });
  }

  @Override
  public void onError(final RaftException e) {
    LOG.error("Raft error: %s", e, e);
  }

  @Override
  public boolean onSnapshotLoad(final SnapshotReader reader) {
    if (isLeader()) {
      LOG.warn("Leader is not supposed to load snapshot");
      return false;
    }
    if (reader.getFileMeta("data") == null) {
      LOG.error("Fail to find data file in {}", reader.getPath());
      return false;
    }
    final CounterSnapshotFile snapshot =
        new CounterSnapshotFile(reader.getPath() + File.separator + "data");
    try {
      this.value.set(snapshot.load());
      return true;
    } catch (final IOException e) {
      LOG.error("Fail to load snapshot from {}", snapshot.getPath());
      return false;
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

}