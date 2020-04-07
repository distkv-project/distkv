package com.distkv.server.storeserver.runtime;

import com.distkv.core.LogEntry;
import com.distkv.core.LogEntry.LogEntryBuilder;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.DistkvRequest;
import com.distkv.rpc.protobuf.generated.DistkvProtocol.RequestType;
import com.distkv.server.storeserver.Wal;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.HashMap;
import java.util.Map;

/**
 * Manage the Master-slave synchronize for log entries.
 * <p>
 * Followers synchronize the log by pulling.
 */
public class LogSyncManager {

  private Wal wal;
  private final StoreRuntime runtime;
  // node id -> log index.
  private Map<String, Integer> nodeLogIndexMap = new HashMap<>(3);

  public LogSyncManager(StoreRuntime runtime) {
    this.runtime = runtime;
  }

  /**
   * Update the group information when leander and followers changed.
   */
  public void updateMembers() {

  }

  public void onRequest(DistkvRequest request) throws InvalidProtocolBufferException {

    // the ack request from followers:
    if (request.getRequestType().equals(RequestType.SYNC_ACK)) {
      // 1. process the ack from follower.,
      processFollowerAck(request);
      // 2. commit the log,
      commitAndSync();
      // 3. sync new log entries.
      syncLogEntry();
    } else if (request.getRequestType().equals(RequestType.SYNC_COMMIT)) {
      // the request from master:
      // 1. commit request, do commit.
      commit();
    } else if (request.getRequestType().equals(RequestType.SYNC_APPEND_LOG)) {
      // 2. append request, append the log to wal, send back ack.
      appendToWal(request.getRequest().unpack(DistkvRequest.class));
    } else {
      // the request from clients
      // append the log to wal.

    }
  }

  private void processFollowerAck(DistkvRequest request) {
    // TODO

  }

  private void commitAndSync() {

  }

  private void commit() {

  }

  private void syncLogEntry() {

  }

  /**
   * Check the request from client whether need to synchronize or not.
   * @param request the request from client.
   * @return true when synchronization is needed, otherwise is false.
   */
  private boolean isNeededSync(DistkvRequest request) {
    return false;
  }

  private void appendToWal(DistkvRequest request) {
    LogEntry logEntry = new LogEntryBuilder()
        .withLogIndex(wal.nextIndex())
        .withValue(request.toByteArray())
        .build();
    wal.append(logEntry);
  }
}
