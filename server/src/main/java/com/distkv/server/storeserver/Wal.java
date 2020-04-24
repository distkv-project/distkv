package com.distkv.server.storeserver;

import com.distkv.core.LogEntry;
import com.distkv.core.segment.LogSegment;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * a WAL Implement.
 * </p>
 * The interface for wal is not stable now.  it will change for upper logical.
 */
public class Wal {

  private LogSegment segment = new LogSegment();
  private int lastCommitIndex;
  private ReentrantLock writeLock = new ReentrantLock();

  // this used by follower
  public void append(LogEntry logEntry) {
    try {
      writeLock.lock();
      segment.appendLogEntry(logEntry);
    } finally {
      writeLock.unlock();
    }
  }

  public LogEntry get(int logIndex) {
    return segment.getLogEntry(logIndex);
  }

  public List<LogEntry> commit(int commitIndex) {
    if (commitIndex > lastCommitIndex) {
      List<LogEntry> entries = segment.getLogEntries(lastCommitIndex + 1, commitIndex);
      lastCommitIndex = commitIndex;
      return entries;
    } else {
      return Collections.emptyList();
    }
  }

}
